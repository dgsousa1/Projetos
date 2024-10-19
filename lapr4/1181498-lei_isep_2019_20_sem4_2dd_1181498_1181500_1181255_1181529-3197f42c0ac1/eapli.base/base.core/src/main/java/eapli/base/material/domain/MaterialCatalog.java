package eapli.base.material.domain;


import java.util.List;


public class MaterialCatalog {

    /**
     * Gets the list of all materials.
     */
    private List<Material> materials;

    /**
     * Returns the materials.
     * @return materials
     */
    public List<Material> getMaterials() {
        return materials;
    }

    /**
     * Modifies the materials.
     * @param materials
     */
    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    @Override
    public String toString() {
        return "MaterialCatalog{" + "materials=" + materials + '}';
    }
}


