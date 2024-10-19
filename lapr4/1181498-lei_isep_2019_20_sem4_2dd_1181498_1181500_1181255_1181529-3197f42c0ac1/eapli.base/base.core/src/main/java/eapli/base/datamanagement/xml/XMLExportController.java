package eapli.base.datamanagement.xml;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.material.domain.Material;
import eapli.base.material.domain.MaterialCategory;
import eapli.base.material.repositories.MaterialRepository;
import eapli.base.product.domain.BillOfMaterials;
import eapli.base.product.domain.Product;
import eapli.base.product.repositories.BillOfMaterialsRepository;
import eapli.base.product.repositories.ProductRepository;
import eapli.base.productionline.domain.ProductionLine;
import eapli.base.productionline.repositories.ProductionLineRepository;
import eapli.base.productionorder.domain.ProductionOrder;
import eapli.base.productionorder.repositories.ProductionOrderRepository;
import eapli.base.storagearea.domain.StorageArea;
import eapli.base.storagearea.repositories.StorageAreaRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class XMLExportController {
    XMLExport xmlExport = new XMLExport();
    private final MaterialRepository materialRepository = PersistenceContext.repositories().materials();
    private final ProductRepository productRepository = PersistenceContext.repositories().product();
    private final BillOfMaterialsRepository billOfMaterialsRepository = PersistenceContext.repositories().billsofmaterials();
    private final ProductionOrderRepository productionOrderRepository = PersistenceContext.repositories().productionOrders();
    private final ProductionLineRepository productionLineRepository = PersistenceContext.repositories().productionLines();
    private final StorageAreaRepository storageAreaRepository = PersistenceContext.repositories().storageArea();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Method that prepares the objects to send to the XMLExporter
     * @param filename filepath of the file to be exported
     * @param from LocalDateTime object that represents from a date forwards, can be null depending on user choice
     * @param until LocalDateTime object that represents until a date , can be null depending on user choice
     * @param showMaterialCategories true if user wants to see the material categories, else false
     * @param showProductCategories true if user wants to see the product categories, else false
     * @param showMaterialCatalog true if user wants to see the material catalog, else false
     * @param showProductCatalog true if user wants to see the product catalog, else false
     * @param showBillOfMaterials true if user wants to see the bills of materials, else false
     * @param showProductionOrders true if user wants to see production orders, else false
     * @param showProductionLines true if user wants to see production lines, else false
     * @param showStorageAreas true if user wants to see storage areas, else false
     * @return boolean true if successful, false if any exception happened
     */
    public boolean exportIntoXML(String filename ,LocalDateTime from, LocalDateTime until, boolean showMaterialCategories,boolean showProductCategories,boolean showMaterialCatalog
    ,boolean showProductCatalog, boolean showBillOfMaterials, boolean showProductionOrders, boolean showProductionLines, boolean showStorageAreas){

        /* Will get all material categories from the database, if user requested them for export */
        List<MaterialCategory> materialCategories= new ArrayList<>();
        if(showMaterialCategories){
            materialCategories = materialRepository.findCategory();
        }

        /* Will get all product categories from the database, if user requested them for export */
        List<String> productCategories = new ArrayList<>();
        if(showProductCategories){
            productCategories = productRepository.listAllProductCategories();
        }

        /* Will get all materials from the database, if user requested them for export */
        List<Material> materialList = new ArrayList<>();
        if(showMaterialCatalog){
            materialList = materialRepository.listAllMaterials();
        }

        /* Will get all products from the database, if user requested them for export */
        List<Product> productList = new ArrayList<>();
        if(showProductCatalog){
            productList = productRepository.listAllProducts();
        }

        /* Will get all bills of materials from the database, if user requested them for export, and will sort them out
        * depending on the temporal filter chosen by the user */
        Iterable<BillOfMaterials> billOfMaterials;
        List<BillOfMaterials> preparedBillOfMaterials = new ArrayList<>();
        if(showBillOfMaterials){
            billOfMaterials = billOfMaterialsRepository.findAll();
            if(from != null  && until == null){
                //User chose time filtering: from a date forwards
                for(BillOfMaterials bm : billOfMaterials){
                    if(bm.getIssueDate().isAfter(from)){
                       preparedBillOfMaterials.add(bm);
                    }
                }
            }else if(from == null && until!=null){
                //User chose time filtering: until a date
                for(BillOfMaterials bm : billOfMaterials){
                    if(bm.getIssueDate().isBefore(until)){
                        preparedBillOfMaterials.add(bm);
                    }
                }
            }else if(from != null && until!=null){
                //User chose time filtering: between two dates
                for(BillOfMaterials bm : billOfMaterials){
                    if(bm.getIssueDate().isAfter(from)&& bm.getIssueDate().isBefore(until)){
                        preparedBillOfMaterials.add(bm);
                    }
                }
            }
        }

        /* Will get all production orders from the database, if user requested them for export, and will sort them out
         * depending on the temporal filter chosen by the user */
        Iterable<ProductionOrder> productionOrders;
        List<ProductionOrder> preparedProductionOrders = new ArrayList<>();
        if(showProductionOrders){
            productionOrders = productionOrderRepository.findAll();
                if(from != null  && until == null){
                    //User chose time filtering: from a date forwards
                    for(ProductionOrder po : productionOrders){
                        if(po.getIssueDate().isAfter(from)){
                            preparedProductionOrders.add(po);
                        }
                    }
                }else if(from == null && until!=null){
                    //User chose time filtering: until a date
                    for(ProductionOrder po : productionOrders) {
                        if (po.getIssueDate().isBefore(until)) {
                            preparedProductionOrders.add(po);
                        }
                    }
                }else if(from != null && until!=null){
                    //User chose time filtering: between two dates
                    for(ProductionOrder po : productionOrders){
                        if(po.getIssueDate().isAfter(from) && po.getIssueDate().isBefore(until)){
                            preparedProductionOrders.add(po);
                        }
                    }
                }
        }

        /* Will get all production lines from the database, if user requested them for export */
        List<ProductionLine> productionLines = new ArrayList<>();
        if(showProductionLines){
            productionLines = productionLineRepository.listAllProductionLines();
        }

        /* Will get all storage areas from the database, if user requested them for export */
        List<StorageArea> storageAreas = new ArrayList<>();
        if(showStorageAreas){
            storageAreas = storageAreaRepository.listAllStorageAreas();
        }

        /*sends all the lists to the XMLExporter, they can be empty if the user does not want to export that type of object*/
        return xmlExport.exportToXML(filename,materialCategories,productCategories,materialList,productList,preparedBillOfMaterials
        ,preparedProductionOrders,productionLines,storageAreas);

    }
    
}
