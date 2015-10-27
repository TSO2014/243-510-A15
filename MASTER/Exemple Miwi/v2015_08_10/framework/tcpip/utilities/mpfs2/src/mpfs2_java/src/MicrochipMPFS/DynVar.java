/*******************************************************************************
  MPFS2 Utility for Microchip TCP/IP Stack
    http_print.h file generation and dynamic variable parsing.

  Company:
    Microchip Technology Inc.

  File Name:
    DynVar.java

  Summary:
    

  Description:
    Developed in NetBeans IDE 8.0, Java version 1.8.0
 *******************************************************************************/

//DOM-IGNORE-BEGIN
/*******************************************************************************
Copyright (c) <2014> released Microchip Technology Inc.  All rights reserved.

Microchip licenses to you the right to use, modify, copy and distribute
Software only when embedded on a Microchip microcontroller or digital signal
controller that is integrated into your product or third party product
(pursuant to the sublicense terms in the accompanying license agreement).

You should refer to the license agreement accompanying this Software for
additional information regarding your rights and obligations.

SOFTWARE AND DOCUMENTATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND,
EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION, ANY WARRANTY OF
MERCHANTABILITY, TITLE, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE.
IN NO EVENT SHALL MICROCHIP OR ITS LICENSORS BE LIABLE OR OBLIGATED UNDER
CONTRACT, NEGLIGENCE, STRICT LIABILITY, CONTRIBUTION, BREACH OF WARRANTY, OR
OTHER LEGAL EQUITABLE THEORY ANY DIRECT OR INDIRECT DAMAGES OR EXPENSES
INCLUDING BUT NOT LIMITED TO ANY INCIDENTAL, SPECIAL, INDIRECT, PUNITIVE OR
CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA, COST OF PROCUREMENT OF
SUBSTITUTE GOODS, TECHNOLOGY, SERVICES, OR ANY CLAIMS BY THIRD PARTIES
(INCLUDING BUT NOT LIMITED TO ANY DEFENSE THEREOF), OR OTHER SIMILAR COSTS.
 *******************************************************************************/
//DOM-IGNORE-END

package MicrochipMPFS;

import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class DynVar {

    int offSetCounter = 0;
    int parseItrtn=0;
    int tempFileRcrdLen=0;
    //private Vector<Byte> resizeArray = new Vector(8,8);
        
    private static List<DynamicVariable> vars;
    String regEx = "~(inc:[A-Za-z0-9\\ \\.-_\\/]{1,60}|[A-Za-z0-9_]{0,40}(\\([A-Za-z0-9_,\\ ]*\\))?)~";
    private Pattern parser = Pattern.compile(regEx);

    private String projectSourceCodeDir;

    String HTTPPRINT_H_HEADER =
        "/*****************************************************************************\r\n" +
        "  AUTO-GENERATED CODE:  Microchip MPFS Generator version: 2.2.2\r\n" +
        "\r\n" +
        "  Microchip TCP/IP Stack Application Demo\r\n" +
        "\r\n" +
        "  Company:\r\n" +
        "    Microchip Technology Inc.\r\n" +
        "\r\n" +
        "  File Name:\r\n" +
        "    http_print.h\r\n" +
        "\r\n" +
        "  Summary:\r\n" +
        "    This file is automatically generated by the MPFS Generator Utility.\r\n" +
        "    ALL MODIFICATIONS WILL BE OVERWRITTEN BY THE MPFS GENERATOR.\r\n" +
        "\r\n" +
        "  Description:\r\n" +
        "    Provides callback headers and resolution for user's custom\r\n" +
        "    HTTP Application.\r\n" +
        " *****************************************************************************/\r\n\r\n" +

        "// DOM-IGNORE-BEGIN\r\n" +
        "/*****************************************************************************\r\n" +
        "Software License Agreement\r\n" +
        "Copyright(c) 2014 Microchip Technology Inc. All rights reserved.\r\n" +
        "Microchip licenses to you the right to use, modify, copy and distribute\r\n" +
        "Software only when embedded on a Microchip microcontroller or digital\r\n" +
        "signal controller that is integrated into your product or third party\r\n" +
        "product (pursuant to the sublicense terms in the accompanying license\r\n" +
        "agreement).\r\n" +
        "\r\n" +
        "You should refer to the license agreement accompanying this Software\r\n" +
        "for additional information regarding your rights and obligations.\r\n" +
        "\r\n" +
        "SOFTWARE AND DOCUMENTATION ARE PROVIDED \"AS IS\" WITHOUT WARRANTY OF ANY\r\n" +
        "KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION, ANY WARRANTY\r\n" +
        "OF MERCHANTABILITY, TITLE, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR\r\n" +
        "PURPOSE. IN NO EVENT SHALL MICROCHIP OR ITS LICENSORS BE LIABLE OR\r\n" +
        "OBLIGATED UNDER CONTRACT, NEGLIGENCE, STRICT LIABILITY, CONTRIBUTION,\r\n" +
        "BREACH OF WARRANTY, OR OTHER LEGAL EQUITABLE THEORY ANY DIRECT OR INDIRECT\r\n" +
        "DAMAGES OR EXPENSES INCLUDING BUT NOT LIMITED TO ANY INCIDENTAL, SPECIAL,\r\n" +
        "INDIRECT, PUNITIVE OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA,\r\n" +
        "COST OF PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY, SERVICES, OR ANY\r\n" +
        "CLAIMS BY THIRD PARTIES (INCLUDING BUT NOT LIMITED TO ANY DEFENSE THEREOF),\r\n" +
        "OR OTHER SIMILAR COSTS.\r\n" +
        " *****************************************************************************/\r\n" +
        "// DOM-IGNORE-END\r\n\r\n" +

        "#ifndef __HTTPPRINT_H_\r\n" +
        "#define __HTTPPRINT_H_\r\n\r\n" +

        "/*****************************************************************************\r\n" +
        " * SECTION:  Includes\r\n" +
        " *****************************************************************************/\r\n" +
        "#include \"tcpip/tcpip.h\"\r\n\r\n" +

        "/*****************************************************************************\r\n" +
        " * SECTION:  Global Variables\r\n" +
        " *****************************************************************************/\r\n" +
        "#if defined(STACK_USE_HTTP2_SERVER)\r\n\r\n" +

        "extern HTTP_STUB httpStubs[MAX_HTTP_CONNECTIONS];\r\n" +
        "extern uint8_t curHTTPID;\r\n\r\n" +

        "/*****************************************************************************\r\n" +
        " * SECTION:  Generated Function Prototypes\r\n" +
        " *****************************************************************************/\r\n" +
        "void HTTPPrint(uint32_t callbackID);\r\n";

    String HTTPPRINT_H_MIDDLE =
        "\r\n" +
        "/*****************************************************************************\r\n" +
        " * FUNCTION: HTTPPrint\r\n" +
        " *\r\n" +
        " * RETURNS:  None\r\n" +
        " *\r\n" +
        " * PARAMS:   callbackID\r\n" +
        " *****************************************************************************/\r\n" +
        "void HTTPPrint(uint32_t callbackID)\r\n" +
        "{\r\n" +
        "   switch(callbackID)\r\n" +
        "   {\r\n";

    String HTTPPRINT_H_FOOTER =
        "       default:\r\n" +
        "           // Output notification for undefined values\r\n" +
        "           TCPPutROMArray(sktHTTP, (ROM uint8_t*)\"!DEF\", 4);\r\n" +
        "   }\r\n\r\n" +
        "   return;\r\n" +
        "}\r\n\r\n" +
        "void HTTPPrint_(void)\r\n" +
        "{\r\n" +
        "   TCPPut(sktHTTP, '~');\r\n" +
        "   return;\r\n" +
        "}\r\n\r\n#endif /*STACK_USE_HTTP2_SERVER*/\r\n\r\n#endif /*__HTTPPRINT_H_*/\r\n";

/*
 * Collect the Dynamic variable in the http_print.idx file.
 */
    public DynVar(String path)
    {
        this.projectSourceCodeDir = path;
        vars = new LinkedList<DynamicVariable>();

        // Read previous index file if it exists.
        try
        {
            File file_exists = new File(projectSourceCodeDir + "http_print.idx");
            if(file_exists.exists() && file_exists.length()>0)
            {
                FileInputStream inputFile = new FileInputStream(projectSourceCodeDir + "http_print.idx");
                DataInputStream in = new DataInputStream(inputFile);
                BufferedReader fin = new BufferedReader(new InputStreamReader(in));
                String s = fin.readLine();

                if (!s.contains("|"))
                {
                    while (s != null)
                    {
                        DynamicVariable dv = new DynamicVariable(s);
                        vars.add(dv);
                        s = fin.readLine();
                    }
                }
                fin.close();
            }
        }
        catch(IOException e)
        {
            // do nothing...just won't have old index information
        }
    }

    /// <summary>
    /// Parses and indexes a file for dynamic variables
    /// </summary>
    /// <param name="file">The MPFSFileRecord to parse</param>
    /// <returns>An MPFSFileRecord of indexes, or null if no variables were found</returns>
    public MPFSFileRecord Parse(MPFSFileRecord file,StringBuilder strLine)
    {
        int dynVarCntr=0;
        //Vector<Byte> resizeArray = new Vector(8,8);
        ByteArrayOutputStream resizeArray = new ByteArrayOutputStream();
        Matcher matches = parser.matcher(strLine);
        
        while(matches.find())
        {
             int i = GetIndex(matches.group().replace(" ","").replace("~",""));

            resizeArray.write((byte)matches.start());
            resizeArray.write((byte)(matches.start()>>8));
            resizeArray.write((byte)(matches.start()>>16));
            resizeArray.write((byte)(matches.start()>>24));
            resizeArray.write((byte)i);
            resizeArray.write((byte)(i>>8));
            resizeArray.write((byte)(i>>16));
            resizeArray.write((byte)(i>>24));
      
            file.dynVarOffsetAndIndexID.addElement((byte)(matches.start() >> 0));
            file.dynVarOffsetAndIndexID.addElement((byte)(matches.start() >> 8));
            file.dynVarOffsetAndIndexID.addElement((byte)(matches.start() >> 16));
            file.dynVarOffsetAndIndexID.addElement((byte)(matches.start() >> 24));
            file.dynVarOffsetAndIndexID.addElement((byte)(i >> 0));
            file.dynVarOffsetAndIndexID.addElement((byte)(i >> 8));
            file.dynVarOffsetAndIndexID.addElement((byte)(i >> 16));
            file.dynVarOffsetAndIndexID.addElement((byte)(i >> 24));
            
            file.dynVarCntr = ++dynVarCntr;
            offSetCounter = offSetCounter + 8;
        }

        if(parseItrtn == (int)0x0)
        {
             file.fileRecordOffset = (int)0x0;
             offSetCounter = (int)0x0;
        }
        else
        {
              file.fileRecordOffset=tempFileRcrdLen;
        }
        if(file.dynVarCntr != 0)
        {
            file.fileRecordLength = 4 /* 4 bytes for file record length itself*/
                                    +2 /*To store the hasIndex/isZipped flag*/
                                    //+(UInt32)file.FileName.Length
                                    + file.dynVarCntr*8;

            tempFileRcrdLen += file.fileRecordLength;

            parseItrtn++;
        }


        // Determine if any matches were made
        if (resizeArray.size() == 0)
            return null;
        else
        {
            // Set up new file record
            MPFSFileRecord idxFile = new MPFSFileRecord();
            idxFile.SetFileName("");   //.FileName = "";
            idxFile.SetFiledate(file.fileDate);
            idxFile.isIndex = true;
            idxFile.data = resizeArray.toByteArray();
            idxFile.fileSizeLen = resizeArray.size();
            return idxFile;
        }

    }

    /// <summary>
    /// Writes out http_print.h and http_print.idx if necessary
    /// </summary>
    /// <returns>TRUE if the files were written, FALSE if no changes are needed</returns>
    public boolean WriteIndices()
    {
        // Determine if an update is necessary
        boolean isChanged = false;
        for(DynamicVariable dv : vars)
        {
            if ((dv.getWasUsed() && dv.getCount() == 0) ||
                (!dv.getWasUsed() && dv.getCount() != 0))
            {
                isChanged = true;
                break;
            }
        }
        if (!isChanged)
            return false;

        // Write out http_print.idx
        File httpFileIdx = new File(projectSourceCodeDir +"http_print.idx");
        try{
        FileOutputStream file_output = new FileOutputStream(httpFileIdx);
        DataOutputStream data_out = new DataOutputStream (file_output);
        BufferedWriter fout = new BufferedWriter(new OutputStreamWriter(data_out));
               
        for(DynamicVariable dv : vars)
        {
            if (dv.getCount() > 0)
                fout.write('+');
            fout.write(dv.GetName());
            fout.write('\n');
        }
        fout.flush();
        fout.close();

        // Begin writing http_print.h
        file_output = new FileOutputStream(projectSourceCodeDir + "http_print.h");
        data_out = new DataOutputStream (file_output);
        fout = new BufferedWriter(new OutputStreamWriter(data_out));
        fout.write(HTTPPRINT_H_HEADER);

        // Write the prototypes
        List<String> prototypes = new ArrayList<String>();
        Pattern rGetParts = Pattern.compile("([A-Za-z0-9_]{0,40})(\\(([A-Za-z0-9_,]*)\\))?");
        for(DynamicVariable dv : vars)
        {
            if (dv.GetName().startsWith("inc:") || dv.getCount() == 0)
                continue;
            Matcher m = rGetParts.matcher(dv.GetName());
            if(m.find()){
            if (prototypes.contains(m.group(1)))
                continue;

            // Add the prototype
            prototypes.add(m.group(1));
            String temp_str = "void HTTPPrint_" + m.group(1) + "(";
            fout.write(temp_str);
            if(m.group(3) != null)
            {
                if(m.groupCount() == 3 && m.group(3).length() > 0)
                {
                    int numParams = m.group(3).split(",").length;
                    for (int i = 0; i < numParams - 1; i++)
                        fout.write("uint16_t,");
                    fout.write("uint16_t");
                }
            }
            else
            {
                fout.write("void");
            }
            fout.write(");\r\n");
            }
        }

        // Write the function itself
        fout.write(HTTPPRINT_H_MIDDLE);
        int index = 0;
        for(DynamicVariable dv : vars)
        {
            if (dv.getCount() == 0)
            {
                index++;
                continue;
            }
            String temp_str  = "        case 0x"+String.format("%08x",index++)+":\r\n";
            fout.write(temp_str);

            // Write the actual case statement
            if(dv.GetName().startsWith("inc:"))
            {
                temp_str = "\t\t\tHTTPIncFile((ROM uint8_t*)\"" + dv.GetName().substring(4) + "\");\r\n\t\t\tbreak;\r\n";
                fout.write(temp_str);
            }
            else
            {
                temp_str = "\t\t\tHTTPPrint_" + dv.GetName();
                fout.write(temp_str);
                if(!dv.GetName().endsWith(")"))
                    fout.write("()");
                fout.write(";\r\n\t\t\tbreak;\r\n");
            }
        }

        // Write the footer part
        fout.write(HTTPPRINT_H_FOOTER);
        fout.flush();
        fout.close();
        }catch (IOException e){}
        return true;
    }

    /// <summary>
    /// Finds the index of a dynamic variable, or creates a new one
    /// </summary>
    /// <param name="name"></param>
    /// <returns>The index of the dynamic variable</returns>
    private int GetIndex(String name)
    {
        int count=0;
        // Search for the dynamic variable
        DynamicVariable dv = new DynamicVariable(name);
       
        int i = vars.indexOf(dv);
        // If not found, add a new one
        if (i == -1)
        {
            vars.add(dv);
            i = vars.size()-1;
        }
        // Mark as used and return the index
        count = vars.get(i).getCount();
        vars.get(i).setCount(++count);
       
        return i;
    }
}

/*
 * Dynamic variable class to count and find out the instance how many times
 * it is being used.
 */
class DynamicVariable
{
    private String name;
    private boolean wasUsed;
    private int count;

    /// <summary>
    /// Gets or sets the name of this DynamicVariable
    /// </summary>
    public String GetName()
    {
        return this.name;
    }

    public void SetName(String value)
    {
        this.name = value;
    }

    /// <summary>
    /// Indicates if this specific instance was previously used
    /// </summary>
    public boolean getWasUsed()
    {
       return this.wasUsed;
    }
    public void setWasUsed(boolean value)
    {
        this.wasUsed = value;
    }

    /// <summary>
    /// Indicates how many times this instance is used
    /// </summary>
    public int getCount()
    {
       return this.count;
    }

    public void setCount(int value)
    {
       this.count = value;
    }
    private long _offsetCntr = 0;
    public long getOffsetCntr()
    {
        return this._offsetCntr;
    }

    public void setOffsetCntr(long value)
    {
         this._offsetCntr = value;
    }

    public DynamicVariable(String name)
    {
        String regEx = "[\\ \\+]";
        this.wasUsed = name.startsWith("+");
        //Pattern pattern = Pattern.compile(regEx);
        //Matcher match = pattern.matcher(name);
        //if(match.find())
        //    this.name = pattern.matcher(name).replaceFirst("");
        //else
        this.name = name.replaceFirst(regEx,"");
        //this.name = matches.group().replaceFirst(regEx,"");
        this.count = 0;
    }

    public boolean equals(Object obj)
    {
        if (obj instanceof DynamicVariable)
            return ((DynamicVariable)obj).GetName().contentEquals(this.name);
        else
            return false;
    }

    public int hashCode()
    {
        return this.name.hashCode();
    }
}
