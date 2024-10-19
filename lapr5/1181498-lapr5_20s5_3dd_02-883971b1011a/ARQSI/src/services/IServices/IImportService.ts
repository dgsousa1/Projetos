
export default interface IImportService  {
    importNodes() :Promise<any[]>;
    importVehicleTypes() :Promise<any[]>;
    importDriverTypes() :Promise<any[]>;
    importPaths() :Promise<any[]>;
    importLines(): Promise<any[]>
    insertIntoLogger(string :any);
}