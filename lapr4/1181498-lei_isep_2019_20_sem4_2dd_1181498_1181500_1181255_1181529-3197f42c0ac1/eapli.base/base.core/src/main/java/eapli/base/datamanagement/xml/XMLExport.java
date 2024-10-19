package eapli.base.datamanagement.xml;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import eapli.base.machine.domain.Machine;
import eapli.base.material.domain.Material;
import eapli.base.material.domain.MaterialCategory;
import eapli.base.product.domain.BillOfMaterials;
import eapli.base.product.domain.Product;
import eapli.base.productionline.domain.ProductionLine;
import eapli.base.productionorder.domain.ProductionOrder;
import eapli.base.storagearea.domain.Batch;
import eapli.base.storagearea.domain.StorageArea;
import eapli.base.utils.MeasuredRawMaterial;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

//SOURCES
// https://examples.javacodegeeks.com/core-java/xml/parsers/documentbuilderfactory/create-xml-file-in-java-using-dom-parser-example/
//https://stackoverflow.com/questions/11275988/how-to-force-java-xml-dom-to-produce-newline-after-xml-version-1-0-encoding

public class XMLExport {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Method to export the factory floor objects to a XML
     * @param filename name/path of the file to be exported
     * @param materialCategoryList List of Material Categories
     * @param productCategoryList List of Product Categories
     * @param materialCatalogList List of all Materials
     * @param productCatalogList List of all Products
     * @param billOfMaterialsList List of all Bills Of Material
     * @param productionOrdersList List of all Production Orders
     * @param productionLinesList List of all Production Lines
     * @param storageAreasList List of all Storage Areas
     * @return true after the method has finished
     */
    public boolean exportToXML(String filename, List<MaterialCategory> materialCategoryList, List<String> productCategoryList
            , List<Material> materialCatalogList, List<Product> productCatalogList, List<BillOfMaterials> billOfMaterialsList, List<ProductionOrder> productionOrdersList,
                            List<ProductionLine> productionLinesList, List<StorageArea> storageAreasList) {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element sffm = document.createElement("sffm");
            document.appendChild(sffm);

            //sffm.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance","xsi:noNamespaceSchemaLocation","sffm.xsd");
            Attr xmlns = document.createAttribute("xmlns");
            xmlns.setValue("http://www.dei.isep.ipp.pt/lprog/sffm");
            sffm.setAttributeNode(xmlns);

            Attr xmlnsXSI = document.createAttribute("xmlns:xsi");
            xmlnsXSI.setValue("http://www.w3.org/2001/XMLSchema-instance");
            sffm.setAttributeNode(xmlnsXSI);

            Attr schemaLocation = document.createAttribute("xsi:schemaLocation");
            schemaLocation.setValue("http://www.dei.isep.ipp.pt/lprog/sffm sffm.xsd");
            sffm.setAttributeNode(schemaLocation);

            //LIST ALL MATERIAL CATEGORIES
            /* Will only create this element if there are any in the parameter list */
            if(materialCategoryList.size()!=0) {

                Element materialCategories = document.createElement("materialCategories");
                sffm.appendChild(materialCategories);

                for (MaterialCategory mc : materialCategoryList) {
                    Element materialCategory = document.createElement("materialCategory");
                    materialCategories.appendChild(materialCategory);

                    Attr materialCategoryCode = document.createAttribute("code");
                    materialCategoryCode.setValue(mc.getCategoryCode());
                    materialCategory.setAttributeNode(materialCategoryCode);

                    Element materialCategoryElementDescription = document.createElement("categoryDescription");
                    materialCategoryElementDescription.appendChild(document.createTextNode(mc.getCategoryDescription()));
                    materialCategory.appendChild(materialCategoryElementDescription);

                }
            }

            //LIST ALL PRODUCT CATEGORIES
            /* Will only create this element if there are any in the parameter list */
            if(productCategoryList.size()!=0) {
                Element productCategories = document.createElement("productCategories");
                sffm.appendChild(productCategories);
                for (String category : productCategoryList) {
                    Element productCategory = document.createElement("productCategory");
                    productCategories.appendChild(productCategory);
                    productCategory.appendChild(document.createTextNode(category));
                }
            }

            //LIST MATERIAL CATALOG
            /* Will only create this element if there are any in the parameter list */
            if(materialCatalogList.size()!=0) {
                Element materialCatalog = document.createElement("materialCatalog");
                sffm.appendChild(materialCatalog);

                Element materials = document.createElement("materials");
                materialCatalog.appendChild(materials);

                for (Material m : materialCatalogList) {
                    Element material = document.createElement("material");
                    materials.appendChild(material);

                    Element materialCode = document.createElement("materialCode");
                    Attr materialCodeAtt = document.createAttribute("code");
                    materialCodeAtt.setValue(m.getCode().getMaterialCode());
                    materialCode.setAttributeNode(materialCodeAtt);
                    material.appendChild(materialCode);

                    Element materialName = document.createElement("materialName");
                    materialName.appendChild(document.createTextNode(m.getCode().getMaterialName()));
                    materialCode.appendChild(materialName);

                    Element materialCategoryCodeInMaterial = document.createElement("materialCategoryCode");
                    materialCategoryCodeInMaterial.appendChild(document.createTextNode(m.toDTO().getMaterialCategoryCode()));
                    material.appendChild(materialCategoryCodeInMaterial);

                    Element technicalFile = document.createElement("technicalFile");
                    material.appendChild(technicalFile);

                    Element fileName = document.createElement("fileName");
                    fileName.appendChild(document.createTextNode(m.toDTO().getMaterialFileName()));
                    technicalFile.appendChild(fileName);

                    Element filePath = document.createElement("filePath");
                    filePath.appendChild(document.createTextNode(m.toDTO().getMaterialFilePath()));
                    technicalFile.appendChild(filePath);

                    Element rawMaterial = document.createElement("rawMaterial");
                    material.appendChild(rawMaterial);
                    Attr rawMaterialNameAtt = document.createAttribute("name");
                    rawMaterialNameAtt.setValue(m.toDTO().getRawMaterialName());
                    rawMaterial.setAttributeNode(rawMaterialNameAtt);

                    Element unit = document.createElement("unit");
                    rawMaterial.appendChild(unit);
                    unit.appendChild(document.createTextNode(m.toDTO().getRawMaterialUnit()));
                }
            }

            //LIST ALL PRODUCTS
            /* Will only create this element if there are any in the parameter list */
            if(productCatalogList.size()!=0) {
                Element productCatalog = document.createElement("productCatalog");
                sffm.appendChild(productCatalog);

                Element products = document.createElement("products");
                productCatalog.appendChild(products);


                for (Product p : productCatalogList) {
                    Element product = document.createElement("product");
                    products.appendChild(product);

                    Element productCode = document.createElement("productCode");
                    product.appendChild(productCode);

                    Element manufacturingCode = document.createElement("manufacturingCode");
                    productCode.appendChild(manufacturingCode);
                    manufacturingCode.appendChild(document.createTextNode(p.toDTO().getManufacturingCode()));

                    Element commercialCode = document.createElement("commercialCode");
                    productCode.appendChild(commercialCode);
                    commercialCode.appendChild(document.createTextNode(p.toDTO().getCommercialCode()));

                    Element productDescription = document.createElement("productDescription");
                    product.appendChild(productDescription);

                    Element briefDescription = document.createElement("briefDescription");
                    briefDescription.appendChild(document.createTextNode(p.toDTO().getBriefDescription()));
                    productDescription.appendChild(briefDescription);

                    Element completeDescription = document.createElement("completeDescription");
                    completeDescription.appendChild(document.createTextNode(p.toDTO().getCompleteDescription()));
                    productDescription.appendChild(completeDescription);

                    Element productCategory = document.createElement("productCategory");
                    product.appendChild(productCategory);
                    productCategory.appendChild(document.createTextNode(p.toDTO().getProductCategory()));

                    Element rawMaterial = document.createElement("rawMaterial");
                    product.appendChild(rawMaterial);
                    Attr rawMaterialName = document.createAttribute("name");
                    rawMaterialName.setValue(p.toDTO().getRawMaterialName());
                    rawMaterial.setAttributeNode(rawMaterialName);

                    Element unit = document.createElement("unit");
                    rawMaterial.appendChild(unit);
                    unit.appendChild(document.createTextNode(p.toDTO().getRawMaterialUnit()));

                }
            }

            //LIST BILL OF MATERIALS
            /* Will only create this element if there are any in the parameter list */
            if(billOfMaterialsList.size()!=0) {
                Element billsOfMaterials = document.createElement("billsOfMaterials");
                sffm.appendChild(billsOfMaterials);
                for (BillOfMaterials bm : billOfMaterialsList) {
                    Element billOfMaterials = document.createElement("billOfMaterials");
                    billsOfMaterials.appendChild(billOfMaterials);

                    Element issueDateBOM = document.createElement("issueDate");
                    billOfMaterials.appendChild(issueDateBOM);

                    String issueDateString = bm.getIssueDate().format(formatter);
                    issueDateBOM.appendChild(document.createTextNode(issueDateString));
                    
                    Element finishedProduct = document.createElement("finishedProduct");
                    billOfMaterials.appendChild(finishedProduct);

                    Element productCode = document.createElement("productCode");
                    finishedProduct.appendChild(productCode);

                    Element manufacturingCode = document.createElement("manufacturingCode");
                    productCode.appendChild(manufacturingCode);
                    manufacturingCode.appendChild(document.createTextNode(bm.getFinishedProduct().toDTO().getManufacturingCode()));

                    Element commercialCode = document.createElement("commercialCode");
                    productCode.appendChild(commercialCode);
                    commercialCode.appendChild(document.createTextNode(bm.getFinishedProduct().toDTO().getCommercialCode()));

                    Element productDescription = document.createElement("productDescription");
                    finishedProduct.appendChild(productDescription);

                    Element briefDescription = document.createElement("briefDescription");
                    briefDescription.appendChild(document.createTextNode(bm.getFinishedProduct().toDTO().getBriefDescription()));
                    productDescription.appendChild(briefDescription);

                    Element completeDescription = document.createElement("completeDescription");
                    completeDescription.appendChild(document.createTextNode(bm.getFinishedProduct().toDTO().getCompleteDescription()));
                    productDescription.appendChild(completeDescription);

                    Element productCategory = document.createElement("productCategory");
                    finishedProduct.appendChild(productCategory);
                    productCategory.appendChild(document.createTextNode(bm.getFinishedProduct().toDTO().getProductCategory()));

                    Element rawMaterial = document.createElement("rawMaterial");
                    finishedProduct.appendChild(rawMaterial);
                    Attr rawMaterialName = document.createAttribute("name");
                    rawMaterialName.setValue(bm.getFinishedProduct().toDTO().getRawMaterialName());
                    rawMaterial.setAttributeNode(rawMaterialName);

                    Element unit = document.createElement("unit");
                    rawMaterial.appendChild(unit);
                    unit.appendChild(document.createTextNode(bm.getFinishedProduct().toDTO().getRawMaterialUnit()));

                    Element listOfMaterials = document.createElement("listOfMaterials");
                    billOfMaterials.appendChild(listOfMaterials);

                    List<MeasuredRawMaterial> measuredRawMaterialList = bm.getListOfMaterials();
                    for (MeasuredRawMaterial mrm : measuredRawMaterialList) {
                        Element measuredRawMaterial = document.createElement("measuredRawMaterial");
                        listOfMaterials.appendChild(measuredRawMaterial);

                        Attr measuredRawMaterialQuantity = document.createAttribute("quantity");
                        measuredRawMaterialQuantity.setValue(String.valueOf(mrm.getQuantity()));
                        measuredRawMaterial.setAttributeNode(measuredRawMaterialQuantity);

                        Element rawMaterialMRM = document.createElement("rawMaterial");
                        measuredRawMaterial.appendChild(rawMaterialMRM);
                        Attr rawMaterialNameMRM = document.createAttribute("name");
                        rawMaterialNameMRM.setValue(mrm.getRawMaterial().getRawMaterialName());
                        rawMaterialMRM.setAttributeNode(rawMaterialNameMRM);

                        Element unitMRM = document.createElement("unit");
                        rawMaterialMRM.appendChild(unitMRM);
                        unitMRM.appendChild(document.createTextNode(mrm.getRawMaterial().getProductUnit().toString()));
                    }
                }
            }

            //LIST PRODUCTION ORDER
            /* Will only create this element if there are any in the parameter list */
            if(productionOrdersList.size()!=0) {
                Element productionOrders = document.createElement("productionOrders");
                sffm.appendChild(productionOrders);
                for (ProductionOrder po : productionOrdersList) {
                    Element productionOrder = document.createElement("productionOrder");
                    productionOrders.appendChild(productionOrder);

                    Attr productionOrderCodeAtt = document.createAttribute("code");
                    productionOrderCodeAtt.setValue(po.getProductionOrderCode());
                    productionOrder.setAttributeNode(productionOrderCodeAtt);

                    if (po.getMaterialsConsumed().size() > 0) {
                        Element materialsConsumed = document.createElement("materialsConsumed");
                        productionOrder.appendChild(materialsConsumed);
                        for (MeasuredRawMaterial mr : po.getMaterialsConsumed()) {
                            Element materialConsumed = document.createElement("materialConsumed");
                            materialsConsumed.appendChild(materialConsumed);

                            Attr materialConsumedQuantityAttr = document.createAttribute("quantity");
                            materialConsumedQuantityAttr.setValue(String.valueOf(mr.getQuantity()));
                            materialConsumed.setAttributeNode(materialConsumedQuantityAttr);

                            Element rawMaterialPO = document.createElement("rawMaterial");
                            materialConsumed.appendChild(rawMaterialPO);
                            Attr rawMaterialNamePO = document.createAttribute("name");
                            rawMaterialNamePO.setValue(mr.getRawMaterial().getRawMaterialName());
                            rawMaterialPO.setAttributeNode(rawMaterialNamePO);

                            Element unitPO = document.createElement("unit");
                            rawMaterialPO.appendChild(unitPO);
                            unitPO.appendChild(document.createTextNode(mr.getRawMaterial().getProductUnit().toString()));


                        }

                    }

                    Element productionOrderSchedule = document.createElement("productionOrderSchedule");
                    productionOrder.appendChild(productionOrderSchedule);

                    Element expectedStartOfExecution = document.createElement("expectedStartOfExecution");
                    productionOrderSchedule.appendChild(expectedStartOfExecution);
                    String expectedStartOfExecutionString = po.expectedStartOfExecution().format(formatter);
                    expectedStartOfExecution.appendChild(document.createTextNode(expectedStartOfExecutionString));

                    if (po.realStartOfExecution().isPresent()) {
                        Element realStartOfExecution = document.createElement("realStartOfExecution");
                        productionOrderSchedule.appendChild(realStartOfExecution);
                        String realStartOfExecutionString = po.realStartOfExecution().get().format(formatter);
                        realStartOfExecution.appendChild(document.createTextNode(realStartOfExecutionString));
                    }

                    if (po.endOfExecution().isPresent()) {
                        Element endExecution = document.createElement(("endExecution"));
                        productionOrderSchedule.appendChild(endExecution);
                        String endExecutionString = po.endOfExecution().get().format(formatter);
                        endExecution.appendChild(document.createTextNode(endExecutionString));
                    }

                    Element grossExecutionTime = document.createElement("grossExecutionTime");
                    productionOrderSchedule.appendChild(grossExecutionTime);
                    grossExecutionTime.appendChild(document.createTextNode(String.valueOf(po.displayGrossExecutionTime())));

                    Element effectiveExecutionTime = document.createElement("effectiveExecutionTime");
                    productionOrderSchedule.appendChild(effectiveExecutionTime);
                    effectiveExecutionTime.appendChild(document.createTextNode(String.valueOf(po.displayEffectiveExecutionTime())));

                    Element productionOrderStatus = document.createElement("productionOrderStatus");
                    productionOrder.appendChild(productionOrderStatus);
                    productionOrderStatus.appendChild(document.createTextNode(po.getProductionOrderStatusString()));


                    Element productResult = document.createElement("productResult");
                    productionOrder.appendChild(productResult);
                    Attr productResultQuantityAtt = document.createAttribute("quantity");
                    productResultQuantityAtt.setValue(String.valueOf(po.getProductResult().getQuantity()));
                    productResult.setAttributeNode(productResultQuantityAtt);

                    Element product = document.createElement("product");
                    productResult.appendChild(product);

                    Element productCode = document.createElement("productCode");
                    product.appendChild(productCode);

                    Element manufacturingCode = document.createElement("manufacturingCode");
                    productCode.appendChild(manufacturingCode);
                    manufacturingCode.appendChild(document.createTextNode(po.getProductResult().getProduct().toDTO().getManufacturingCode()));

                    Element commercialCode = document.createElement("commercialCode");
                    productCode.appendChild(commercialCode);
                    commercialCode.appendChild(document.createTextNode(po.getProductResult().getProduct().toDTO().getCommercialCode()));

                    Element productDescription = document.createElement("productDescription");
                    product.appendChild(productDescription);

                    Element briefDescription = document.createElement("briefDescription");
                    briefDescription.appendChild(document.createTextNode(po.getProductResult().getProduct().toDTO().getBriefDescription()));
                    productDescription.appendChild(briefDescription);


                    Element completeDescription = document.createElement("completeDescription");
                    completeDescription.appendChild(document.createTextNode(po.getProductResult().getProduct().toDTO().getCompleteDescription()));
                    productDescription.appendChild(completeDescription);

                    Element productCategory = document.createElement("productCategory");
                    product.appendChild(productCategory);
                    productCategory.appendChild(document.createTextNode(po.getProductResult().getProduct().toDTO().getProductCategory()));

                    Element rawMaterial = document.createElement("rawMaterial");
                    product.appendChild(rawMaterial);
                    Attr rawMaterialName = document.createAttribute("name");
                    rawMaterialName.setValue(po.getProductResult().getProduct().toDTO().getRawMaterialName());
                    rawMaterial.setAttributeNode(rawMaterialName);

                    Element unit = document.createElement("unit");
                    rawMaterial.appendChild(unit);
                    unit.appendChild(document.createTextNode(po.getProductResult().getProduct().toDTO().getRawMaterialUnit()));

                    Element issueDate = document.createElement("issueDate");
                    productionOrder.appendChild(issueDate);

                    String issueDateString = po.getIssueDate().format(formatter);
                    issueDate.appendChild(document.createTextNode(issueDateString));

                    Element associatedCommissions = document.createElement("associatedCommissions");
                    productionOrder.appendChild(associatedCommissions);

                    List<String> associatedCommissionsList = po.getAssociatedCommissions();
                    for (String s : associatedCommissionsList) {
                        Element commission = document.createElement("commission");
                        associatedCommissions.appendChild(commission);
                        commission.appendChild(document.createTextNode(s));
                    }
                }
            }

            //LIST ProductionLines
            /* Will only create this element if there are any in the parameter list */
            if(productionLinesList.size()!=0) {
                Element productionLines = document.createElement("productionLines");
                sffm.appendChild(productionLines);

                for (ProductionLine pl : productionLinesList) {
                    Element productionLine = document.createElement("productionLine");
                    productionLines.appendChild(productionLine);

                    Attr productionLineIdAtt = document.createAttribute("id");
                    productionLineIdAtt.setValue(String.valueOf(pl.identity()));
                    productionLine.setAttributeNode(productionLineIdAtt);

                    Element machines = document.createElement("machines");
                    productionLine.appendChild(machines);

                    List<Machine> machineList = pl.getMachines();
                    for (Machine m : machineList) {
                        Element machine = document.createElement("machine");
                        machines.appendChild(machine);

                        Attr machineSerialNumberAtt = document.createAttribute("serialNumber");
                        machineSerialNumberAtt.setValue(String.valueOf(m.getSerialNumber()));
                        machine.setAttributeNode(machineSerialNumberAtt);

                        Element machineModel = document.createElement("machineModel");
                        machine.appendChild(machineModel);

                        Attr machineModelNameAtt = document.createAttribute("name");
                        machineModelNameAtt.setValue(m.getMachineModel().getModelName());
                        machineModel.setAttributeNode(machineModelNameAtt);

                        Element description = document.createElement("description");
                        machineModel.appendChild(description);
                        description.appendChild(document.createTextNode(m.getMachineModel().getDescription()));

                        Element machineBrand = document.createElement("machineBrand");
                        machineModel.appendChild(machineBrand);
                        machineBrand.appendChild(document.createTextNode(m.getMachineModel().getBrand()));
                    }

                    ProductionOrder po = pl.getProductionOrder();
                    if (po != null) {
                        Element associatedProductionOrder = document.createElement("associatedProductionOrder");
                        productionLine.appendChild(associatedProductionOrder);

                        Attr productionOrderCodeAtt = document.createAttribute("code");
                        productionOrderCodeAtt.setValue(po.getProductionOrderCode());
                        associatedProductionOrder.setAttributeNode(productionOrderCodeAtt);

                        if (po.getMaterialsConsumed().size() > 0) {
                            Element materialsConsumed = document.createElement("materialsConsumed");
                            associatedProductionOrder.appendChild(materialsConsumed);
                            for (MeasuredRawMaterial mr : po.getMaterialsConsumed()) {
                                Element materialConsumed = document.createElement("materialConsumed");
                                materialsConsumed.appendChild(materialConsumed);

                                Attr materialConsumedQuantityAttr = document.createAttribute("quantity");
                                materialConsumedQuantityAttr.setValue(String.valueOf(mr.getQuantity()));
                                materialConsumed.setAttributeNode(materialConsumedQuantityAttr);

                                Element rawMaterialPO = document.createElement("rawMaterial");
                                materialConsumed.appendChild(rawMaterialPO);
                                Attr rawMaterialNamePO = document.createAttribute("name");
                                rawMaterialNamePO.setValue(mr.getRawMaterial().getRawMaterialName());
                                rawMaterialPO.setAttributeNode(rawMaterialNamePO);

                                Element unitPO = document.createElement("unit");
                                rawMaterialPO.appendChild(unitPO);
                                unitPO.appendChild(document.createTextNode(mr.getRawMaterial().getProductUnit().toString()));


                            }

                        }

                        Element productionOrderSchedule = document.createElement("productionOrderSchedule");
                        associatedProductionOrder.appendChild(productionOrderSchedule);

                        Element expectedStartOfExecution = document.createElement("expectedStartOfExecution");
                        productionOrderSchedule.appendChild(expectedStartOfExecution);
                        String expectedStartOfExecutionString = po.expectedStartOfExecution().format(formatter);
                        expectedStartOfExecution.appendChild(document.createTextNode(expectedStartOfExecutionString));

                        if (po.realStartOfExecution().isPresent()) {
                            Element realStartOfExecution = document.createElement("realStartOfExecution");
                            productionOrderSchedule.appendChild(realStartOfExecution);
                            String realStartOfExecutionString = po.realStartOfExecution().get().format(formatter);
                            realStartOfExecution.appendChild(document.createTextNode(realStartOfExecutionString));
                        }

                        if (po.endOfExecution().isPresent()) {
                            Element endExecution = document.createElement(("endExecution"));
                            productionOrderSchedule.appendChild(endExecution);
                            String endExecutionString = po.endOfExecution().get().format(formatter);
                            endExecution.appendChild(document.createTextNode(endExecutionString));
                        }

                        Element grossExecutionTime = document.createElement("grossExecutionTime");
                        productionOrderSchedule.appendChild(grossExecutionTime);
                        grossExecutionTime.appendChild(document.createTextNode(String.valueOf(po.displayGrossExecutionTime())));

                        Element effectiveExecutionTime = document.createElement("effectiveExecutionTime");
                        productionOrderSchedule.appendChild(effectiveExecutionTime);
                        effectiveExecutionTime.appendChild(document.createTextNode(String.valueOf(po.displayEffectiveExecutionTime())));

                        Element productionOrderStatus = document.createElement("productionOrderStatus");
                        associatedProductionOrder.appendChild(productionOrderStatus);
                        productionOrderStatus.appendChild(document.createTextNode(po.getProductionOrderStatusString()));


                        Element productResult = document.createElement("productResult");
                        associatedProductionOrder.appendChild(productResult);
                        Attr productResultQuantityAtt = document.createAttribute("quantity");
                        productResultQuantityAtt.setValue(String.valueOf(po.getProductResult().getQuantity()));
                        productResult.setAttributeNode(productResultQuantityAtt);

                        Element product = document.createElement("product");
                        productResult.appendChild(product);

                        Element productCode = document.createElement("productCode");
                        product.appendChild(productCode);

                        Element manufacturingCode = document.createElement("manufacturingCode");
                        productCode.appendChild(manufacturingCode);
                        manufacturingCode.appendChild(document.createTextNode(po.getProductResult().getProduct().toDTO().getManufacturingCode()));

                        Element commercialCode = document.createElement("commercialCode");
                        productCode.appendChild(commercialCode);
                        commercialCode.appendChild(document.createTextNode(po.getProductResult().getProduct().toDTO().getCommercialCode()));

                        Element productDescription = document.createElement("productDescription");
                        product.appendChild(productDescription);

                        Element briefDescription = document.createElement("briefDescription");
                        briefDescription.appendChild(document.createTextNode(po.getProductResult().getProduct().toDTO().getBriefDescription()));
                        productDescription.appendChild(briefDescription);

                        Element completeDescription = document.createElement("completeDescription");
                        productDescription.appendChild(completeDescription);
                        completeDescription.appendChild(document.createTextNode(po.getProductResult().getProduct().toDTO().getCompleteDescription()));

                        Element productCategory = document.createElement("productCategory");
                        product.appendChild(productCategory);
                        productCategory.appendChild(document.createTextNode(po.getProductResult().getProduct().toDTO().getProductCategory()));

                        Element rawMaterial = document.createElement("rawMaterial");
                        product.appendChild(rawMaterial);
                        Attr rawMaterialName = document.createAttribute("name");
                        rawMaterialName.setValue(po.getProductResult().getProduct().toDTO().getRawMaterialName());
                        rawMaterial.setAttributeNode(rawMaterialName);

                        Element unit = document.createElement("unit");
                        rawMaterial.appendChild(unit);
                        unit.appendChild(document.createTextNode(po.getProductResult().getProduct().toDTO().getRawMaterialUnit()));

                        Element issueDate = document.createElement("issueDate");
                        associatedProductionOrder.appendChild(issueDate);

                        String issueDateString = po.getIssueDate().format(formatter);
                        issueDate.appendChild(document.createTextNode(issueDateString));

                        Element associatedCommissions = document.createElement("associatedCommissions");
                        associatedProductionOrder.appendChild(associatedCommissions);

                        List<String> associatedCommissionsList = po.getAssociatedCommissions();
                        for (String s : associatedCommissionsList) {
                            Element commission = document.createElement("commission");
                            associatedCommissions.appendChild(commission);
                            commission.appendChild(document.createTextNode(s));
                        }

                    }
                }
            }

            //LIST STORAGE AREAS
            /* Will only create this element if there are any in the parameter list */
            if(storageAreasList.size()!=0) {
                Element storageAreas = document.createElement("storageAreas");
                sffm.appendChild(storageAreas);
                for (StorageArea sa : storageAreasList) {
                    Element storageArea = document.createElement("storageArea");
                    storageAreas.appendChild(storageArea);

                    Element storageAreaDescription = document.createElement("storageAreaDescription");
                    storageArea.appendChild(storageAreaDescription);
                    storageAreaDescription.appendChild(document.createTextNode(sa.getStorageDescription()));

                    Element batches = document.createElement("batches");
                    storageArea.appendChild(batches);

                    Set<Batch> batchesSet = sa.getBatches();
                    if (batchesSet.size() != 0) {
                        for (Batch b : batchesSet) {
                            Element batch = document.createElement("batch");
                            batches.appendChild(batch);

                            Attr batchCodeAtt = document.createAttribute("batchCode");
                            batchCodeAtt.setValue(b.getBatchCode());
                            batch.setAttributeNode(batchCodeAtt);

                            Element creationDateTime = document.createElement("creationDateTime");
                            batch.appendChild(creationDateTime);

                            String creationDateTimeString = b.getBatchCreationDate().format(formatter);
                            creationDateTime.appendChild(document.createTextNode(creationDateTimeString));

                            Element measuredRawMaterial = document.createElement("measuredRawMaterial");
                            batch.appendChild(measuredRawMaterial);

                            Attr measuredRawMaterialQuantity = document.createAttribute("quantity");
                            measuredRawMaterialQuantity.setValue(String.valueOf(b.getMaterial().getQuantity()));
                            measuredRawMaterial.setAttributeNode(measuredRawMaterialQuantity);

                            Element rawMaterialMRM = document.createElement("rawMaterial");
                            measuredRawMaterial.appendChild(rawMaterialMRM);

                            Attr rawMaterialNameMRM = document.createAttribute("name");
                            rawMaterialNameMRM.setValue(b.getMaterial().getRawMaterial().getRawMaterialName());
                            rawMaterialMRM.setAttributeNode(rawMaterialNameMRM);

                            Element unitMRM = document.createElement("unit");
                            rawMaterialMRM.appendChild(unitMRM);
                            unitMRM.appendChild(document.createTextNode(b.getMaterial().getRawMaterial().getProductUnit().toString()));
                        }
                    }
                }
            }

            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filename));
            //StreamResult streamResult = new StreamResult(System.out);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
            // If you use
            // StreamResult result = new StreamResult(System.out);
            // the output will be pushed to the standard output ...
            // You can use that for debugging

            transformer.transform(domSource, streamResult);



        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            return false;
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
            return false;
        }
        return true;
    }

}


