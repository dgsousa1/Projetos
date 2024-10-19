package eapli.base.datamanagement.csv;

import eapli.base.datamanagement.DataManager;
import eapli.base.datamanagement.dto.ProductLineDTO;
import eapli.base.datamanagement.dto.ProductionOrderDTO;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.product.domain.Product;
import eapli.base.product.domain.ProductCategory;
import eapli.base.product.domain.ProductCode;
import eapli.base.product.domain.ProductDescription;
import eapli.base.product.repositories.ProductRepository;
import eapli.base.productionorder.domain.ProductionOrder;
import eapli.base.productionorder.domain.ProductionOrderCode;
import eapli.base.productionorder.domain.ProductionOrderSchedule;
import eapli.base.rawmaterial.domain.RawMaterial;
import eapli.base.utils.MeasuredProduct;

import eapli.base.utils.application.UnitController;
import eapli.base.utils.repositories.UnitRepository;
import eapli.framework.validations.Preconditions;
import javafx.print.Printer;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVDataManager implements DataManager {

    private static final String DIVIDER = ";";
    private static final String DIVIDER1 = ",";
    private static final String DATE_FORMAT="yyyy-MM-dd HH:mm";

    private final String ERROR_FILENAME = "ProductWErrors.csv";
    private final String ERROR_FILENAME1 = "ProductionOrdersWErrors.csv";
    UnitController unitController = new UnitController();
    private final ProductRepository productRepository = PersistenceContext.repositories().product();
    final UnitRepository unitRepository = PersistenceContext.repositories().units();



    /**
     * Method that reads through a file, creating the Product objects, and adding error lines to another Set
     * @param filename file path of the file to be imported
     * @return a Set with the products contained in the file
     * @throws IOException errors related to the file
     */
    @Override
    public Set<Product> importProductCatalog(String filename) throws IOException {
        Set<Product> productsSet = new HashSet<>();
        Set<ProductLineDTO> errorLines = new HashSet<>();

            BufferedReader  bf = new BufferedReader(new FileReader(filename));
            String line = bf.readLine(); //Skips the header

            while((line = bf.readLine())!= null) {
                boolean flag = true;
                String[] sb = line.split(DIVIDER);
                //Verifies if any of the parameters of the line read are empty or null
                for (int i = 0; i < sb.length; i++) {
                    try {
                        Preconditions.nonEmpty(sb[i]);
                    }catch(IllegalArgumentException e){
                        errorLines.add(new ProductLineDTO(sb[0],sb[1],sb[2],sb[3],sb[4],sb[5],"Parameter null or empty"));
                        flag=false;
                    }
                }
                if(flag){
                    if(sb[0].length()<=15 && sb[1].length()<=15){
                        if(unitController.isUnitPresent(sb[4])){
                            ProductCode pc = new ProductCode(sb[0], sb[1]);
                            ProductDescription pd = new ProductDescription(sb[2], sb[3]);
                            ProductCategory pcat = new ProductCategory(sb[5]);
                            RawMaterial rawMaterial = new RawMaterial((sb[2]),unitRepository.findUnit(sb[4]).get());
                            Product p = new Product(pc, pd, pcat, rawMaterial);
                            productsSet.add(p);
                        }else{
                            errorLines.add(new ProductLineDTO(sb[0],sb[1],sb[2],sb[3],sb[4],sb[5],"Unit not recognized"));
                        }
                    }else{
                        errorLines.add(new ProductLineDTO(sb[0],sb[1],sb[2],sb[3],sb[4],sb[5],"Code size not allowed"));
                    }

                }

            }
            exportProductsWithErrors(ERROR_FILENAME,errorLines);

        return productsSet;

    }

    /**
     * Method that exports the lines with errors found on the imported file
     * @param filename filepath of the file that will be exported with the errors
     * @param products Set of DTO's with the information of the lines with errors
     * @return boolean true when the method finishes
     * @throws IOException if not file has been found
     */
    @Override
    public boolean exportProductsWithErrors(String filename, Set<ProductLineDTO> products) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("CódigoFabrico;CódigoComercial;Descrição Breve;Descrição Completa;Unidade;Categoria");
            for (ProductLineDTO p : products) {
                writer.newLine();
                writer.write(p.getManufacturingCode() + ";" + p.getComercialCode() + ";"
                        + p.getBriefDescription() + ";" + p.getCompleteDescription() + ";" + p.getUnit()
                        + ";" + p.getProductCategory());
            }
        } catch (IOException e) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, e);
            throw new IOException("No file");
        }
        return true;
    }

    @Override
    public Set<ProductionOrder> importProductionOrders(String filename) throws IOException{
       Set<ProductionOrder> ordersSet = new HashSet<>();
        Set<ProductionOrderDTO> errorLines = new HashSet<>();

        BufferedReader  bf = new BufferedReader(new FileReader(filename));
        String line = bf.readLine(); //Skips the header

        while((line = bf.readLine())!= null) {
            boolean flag = true;
            List<String> readyCommissions = new LinkedList<>();
            LocalDateTime issuedDate = null;
            ProductionOrderSchedule productionOrderSchedule = null;
            MeasuredProduct measuredProduct = null;
            String[] commissions = null;

            String[] sb = line.split(DIVIDER);
            //Verifies if any of the parameters of the line read are empty or null
            for (int i = 0; i < sb.length; i++) {
                try {
                    Preconditions.nonEmpty(sb[i]);
                } catch (IllegalArgumentException e) {
                    errorLines.add(new ProductionOrderDTO(sb[0], sb[1], sb[2], sb[3], sb[4], sb[5], sb[6], "Parameter null or empty"));
                    flag = false;
                }
            }

            if (flag) {

                if (sb[0].length() <= 15) {
                    ProductionOrderCode productionOrderCode = new ProductionOrderCode(sb[0]);

                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
                        issuedDate = LocalDateTime.parse(buildDateString(sb[1]), formatter);
                    } catch (DateTimeParseException e) {
                        errorLines.add(new ProductionOrderDTO(sb[0], sb[1], sb[2], sb[3], sb[4], sb[5], sb[6], "Issue Date format invalid"));
                        flag = false;
                    }
                    if (flag) {
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
                            LocalDateTime startExecution = LocalDateTime.parse(buildDateString(sb[2]), formatter);
                            productionOrderSchedule = new ProductionOrderSchedule(startExecution);
                            if (startExecution.isBefore(LocalDateTime.now())) {
                                errorLines.add(new ProductionOrderDTO(sb[0], sb[1], sb[2], sb[3], sb[4], sb[5], sb[6], "Scheduled execution date is before today"));
                                flag = false;
                            } else if (startExecution.isBefore(issuedDate)) {
                                errorLines.add(new ProductionOrderDTO(sb[0], sb[1], sb[2], sb[3], sb[4], sb[5], sb[6], "Scheduled execution is before issued date"));
                                flag = false;
                            }
                        } catch (DateTimeParseException e) {
                            errorLines.add(new ProductionOrderDTO(sb[0], sb[1], sb[2], sb[3], sb[4], sb[5], sb[6], "Scheduled execution date format invalid"));
                            flag = false;
                        }

                    }
                    if (flag) {
                        Optional<Product> p = productRepository.findByManufacturingCode(sb[3]);
                        if (p.isPresent()) {
                            measuredProduct = new MeasuredProduct(p.get(), Float.parseFloat(sb[4]));
                        } else {
                            errorLines.add(new ProductionOrderDTO(sb[0], sb[1], sb[2], sb[3], sb[4], sb[5], sb[6], "Product doesn't exist in the system"));
                            flag = false;
                        }
                    }
                    if (flag) {
                        try {
                            commissions = sb[6].split(DIVIDER1);
                        }catch(Exception e){
                            errorLines.add(new ProductionOrderDTO(sb[0], sb[1], sb[2], sb[3], sb[4], sb[5], sb[6], "Error on Commission format"));
                            flag=false;
                        }

                    }
                    if(flag){
                        for (String s : commissions) {
                            try{
                            readyCommissions.add(s.replace("\"", ""));
                            }catch(Exception e){
                                errorLines.add(new ProductionOrderDTO(sb[0], sb[1], sb[2], sb[3], sb[4], sb[5], sb[6], "Error on Commission format"));
                                flag=false;
                            }

                        }
                    }
                    if(flag){
                        ordersSet.add(new ProductionOrder(productionOrderCode,productionOrderSchedule,measuredProduct,issuedDate,readyCommissions));
                    }

                } else {
                    errorLines.add(new ProductionOrderDTO(sb[0], sb[1], sb[2], sb[3], sb[4], sb[5], sb[6], "Order code invalid"));
                }
            }
        }
        exportProductionsOrdersWithErrors(ERROR_FILENAME1,errorLines);

        return ordersSet;
    }

    @Override
    public boolean exportProductionsOrdersWithErrors(String filename, Set<ProductionOrderDTO> orders) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("ID_ORDEM;DataEmissão;DataPrevistaExecução;CódigoFabrico;Quantidade;Unidade;Encomendas;MensagemErro");
            for (ProductionOrderDTO o : orders) {
                writer.newLine();
                writer.write(o.getOrderCode()+";"+o.getIssueDate()+";"+o.getExpectedStartOfExecution()+";"+o.getManufacturingCode()
                +";"+o.getQuantity()+";"+o.getUnit()+";"+o.getAssociatedCommissions() + ";" + o.getErrorType());
            }
        } catch (IOException e) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, e);
            throw new IOException("No file");
        }
        return true;
    }

    private String localDateTimeToString(LocalDateTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return time.format(formatter);
    }

    private String buildDateString(String date){
        String year = date.substring(0,4);
        String month = date.substring(4,6);
        String day = date.substring(6);
        return year+"-"+month+"-"+day+" 00:00";

    }


}


