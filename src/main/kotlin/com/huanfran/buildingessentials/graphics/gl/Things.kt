package com.huanfran.buildingessentials.graphics.gl

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.util.*



object Things {


    val FILE_SEP = System.getProperty("file.separator")

    fun insertFileSep(path: String) = path.replace("/", FILE_SEP)



    fun glErrorCheck(value: Int, errorMessage: String) {
        if (value == 0) System.err.println("Error: $errorMessage")
    }



    fun loadShaderCode(file: File): String? {
        var code = ""
        //Try with resources statement.
        try {
            BufferedReader(FileReader(file.path)).use { reader ->
                //For each line of the text file, add it to the string and append a new line.
                while (reader.ready()) {
                    code += reader.readLine() + "\n"
                }
            }
        } catch (e: IOException) {
            return null
        }

        return code
    }



    /**
     * Tests if the ending of a string ends with any of the specified suffixes.
     *
     * @param s
     * @param suffixes
     * @return
     */
    fun endsWith(s: String, suffixes: List<String>): Boolean {
        for(suffix in suffixes)
            if(s.endsWith(suffix))
                return true

        return false
    }



    fun filesInFolder(folder: File): List<File> {
        val files = ArrayList<File>()

        if(folder.exists())
            for(fileEntry in folder.listFiles())
                if(fileEntry.isDirectory)
                    files.addAll(filesInFolder(fileEntry))
                else
                    files.add(fileEntry)

        return files
    }



    /**
     * Gets all the files in multiple folders, including in sub-directories.
     *
     * @param folderPaths
     * @return
     */
    fun filesInFolders(folders: List<File>): List<File> {
        val files = ArrayList<File>()

        for(path in folders)
            files.addAll(filesInFolder(path))

        return files
    }



    /**
     * Determines whether the given object of generic type E is contained multiple times within the given list.
     */
    fun<E> containsDuplicates(list: List<E>, e: E) : Boolean {
        //The number of list items that are the same as e.
        var counter = 0

        for(test in list) {
            if(e == test)
                counter++

            if(counter == 2)
                return true
        }

        return false
    }


}