/* !---- DO NOT EDIT: This file autogenerated by com\sun\gluegen\JavaEmitter.java on Tue May 27 02:37:55 PDT 2008 ----! */

package jogamp.common.os;

import java.security.*;

import com.jogamp.common.os.DynamicLinker;

public class WindowsDynamicLinkerImpl implements DynamicLinker {

  private static boolean DEBUG;

  static {
    AccessController.doPrivileged(new PrivilegedAction() {
        public Object run() {
          DEBUG = (System.getProperty("jogamp.debug.NativeLibrary") != null);
          return null;
        }
      });
  }

  /** Interface to C language function: <br> <code> BOOL FreeLibrary(HANDLE hLibModule); </code>    */
  private static native int FreeLibrary(long hLibModule);

  /** Interface to C language function: <br> <code> DWORD GetLastError(void); </code>    */
  private static native int GetLastError();

  /** Interface to C language function: <br> <code> PROC GetProcAddressA(HANDLE hModule, LPCSTR lpProcName); </code>    */
  private static native long GetProcAddressA(long hModule, java.lang.String lpProcName);

  /** Interface to C language function: <br> <code> HANDLE LoadLibraryW(LPCWSTR lpLibFileName); </code>    */
  private static native long LoadLibraryW(java.lang.String lpLibFileName);


  // --- Begin CustomJavaCode .cfg declarations
  public long openLibraryLocal(String libraryName, boolean debug) {
    // How does that work under Windows ?
    // Don't know .. so it's an alias for the time being
    return openLibraryGlobal(libraryName, debug);
  }
  
  public long openLibraryGlobal(String libraryName, boolean debug) {
    long handle = LoadLibraryW(libraryName);
    if(0==handle && debug) {
        int err = GetLastError();
        System.err.println("LoadLibraryW \""+libraryName+"\" failed, error code: 0x"+Integer.toHexString(err)+", "+err);
    }
    return handle;
  }
  
  public long lookupSymbol(long libraryHandle, String symbolName) {
    String _symbolName = symbolName;
    long addr = GetProcAddressA(libraryHandle, _symbolName);
    if(0==addr) {
        // __stdcall hack: try some @nn decorations,
        //                 the leading '_' must not be added (same with cdecl)
        final int argAlignment=4;  // 4 byte alignment of each argument
        final int maxArguments=12; // experience ..
        for(int arg=0; 0==addr && arg<=maxArguments; arg++) {
            _symbolName = symbolName+"@"+(arg*argAlignment);
            addr = GetProcAddressA(libraryHandle, _symbolName);
        }
    }
    if(DEBUG) {
            System.err.println("WindowsDynamicLinkerImpl.lookupSymbol(0x"+Long.toHexString(libraryHandle)+", "+symbolName+") -> "+_symbolName+", 0x"+Long.toHexString(addr));
    }
    return addr;
  }
  
  public long lookupSymbolGlobal(String symbolName) {
    throw new RuntimeException("lookupSymbolGlobal: Not supported on Windows");
  }

  public void closeLibrary(long libraryHandle) {
    FreeLibrary(libraryHandle);
  }
  // ---- End CustomJavaCode .cfg declarations

} // end of class WindowsDynamicLinkerImpl