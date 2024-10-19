package eapli.base.product.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.product.domain.Product;
import eapli.base.product.repositories.BillOfMaterialsRepository;
import eapli.base.product.domain.BillOfMaterials;
import eapli.base.rawmaterial.domain.RawMaterial;
import eapli.base.rawmaterial.repositories.RawMaterialRepository;
import eapli.base.utils.MeasuredRawMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BillOfMaterialsController {
    private final BillOfMaterialsRepository billOfMaterialsRepository = PersistenceContext.repositories().billsofmaterials();
    RawMaterialRepository rawMaterialRepository = PersistenceContext.repositories().rawmaterials();


    /**
     * Method that creates a new Bill of Materials with the received parameters, and saves it to the Database
     * @param p Product object to be added to the Bill Of Materials
     * @param list List of MeasuredRawMaterials, that are a part of the Bill Of Materials
     * @return the Bill Of Materials saved to the repository
     */
    public BillOfMaterials addBillOfMaterials(Product p, List<MeasuredRawMaterial> list){
        BillOfMaterials billOfMaterials = new BillOfMaterials(p,list);
        return this.billOfMaterialsRepository.save(billOfMaterials);
    }

    /**
     * Lists all products that have no bill of materials.
     * @return product
     */
    public List<Product> productsWithNoBillOfMaterials(){
        return this.billOfMaterialsRepository.productsWithNoBillOfMaterials();
    }

    /**
     *  Lists all raw materials.
     * @return rawmaterial
     */
    public List<RawMaterial> listAllRawMaterials(){
        return this.rawMaterialRepository.listAllRawMaterials();
    }

}
