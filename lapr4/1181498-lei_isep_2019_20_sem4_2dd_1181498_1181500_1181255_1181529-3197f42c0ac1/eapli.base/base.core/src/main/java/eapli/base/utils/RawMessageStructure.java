package eapli.base.utils;

public  class RawMessageStructure {

    private final static String C0 = "Máquina;TipoMsg;DataHora;Produto;Quantidade;Depósito";
    private final static String C9 = "Máquina;TipoMsg;DataHora;Produto;Quantidade;Depósito";
    private final static String P1 = "Máquina;TipoMsg;DataHora;Produto;Quantidade;Lote";
    private final static String P2 = "Máquina;TipoMsg;DataHora;Produto;Quantidade;Depósito";
    private final static String S0 = "Máquina;TipoMsg;DataHora;OrdemProducao";
    private final static String S1 = "Máquina;TipoMsg;DataHora;Erro";
    private final static String S8 = "Máquina;TipoMsg;DataHora";
    private final static String S9 = "Máquina;TipoMsg;DataHora;OrdemProducao";

    public static String getMessageFormat(String type){
        switch(type){
            case "C0":
                return C0;
            case "C9":
                return C9;
            case "P1":
                return P1;
            case "P2":
                return P2;
            case "S0":
                return S0;
            case "S1":
                return S1;
            case "S8":
                return S8;
            case "S9":
                return S9;
            default:
                return null;
        }




    }






}
