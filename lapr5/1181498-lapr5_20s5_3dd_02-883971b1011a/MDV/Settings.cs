using System;

namespace DDDSample1

{

    public static class Settings
    {
        public static string Secret = "5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8";
        public static int MAX_DURATION_IN_SECONDS_VEHICLE_DUTY = 86400;
        public static int MAX_DURATION_IN_SECONDS_DRIVER_DUTY = 28800; //8 hours

        public static string xml_filename_error = "../ARQSI/MDVImportErrorLogs.txt";

        public static string xml_filename_import = "../ARQSI/demo-lapr5.glx.xml";
        //public static string xml_filename_import = "../ARQSI/teste.xml";

        public static int MIN_AGE = 18;
    }

}