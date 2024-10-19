
export class TextUtil {
  public static isUUID(text: string): boolean {
    return new RegExp('\b[0-9a-f]{8}\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\b[0-9a-f]{12}\b').test(text)
  }

  /**
   * Checks if the length of the string is valid 
   * @param name String to be checked
   * @param minLength minimum length of the string
   * @param maxLength maximum length of the string
   * @returns true or false depending on the validity of the string's length
   */
  public static checkLength(name: string, minLength: number, maxLength: number): boolean {
    return name.length > minLength && name.length <= maxLength;
  }

  /**
   * Validates if the string is null or empty
   * @param name string to be checked
   * @returns true or false depending on the validity of the string
   */
  public static validateString(name: string): boolean {
    name.trim();
    if (name === "" || !name) {
      return false;
    }
    return true;
  }



}