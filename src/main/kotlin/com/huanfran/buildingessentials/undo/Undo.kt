package com.huanfran.buildingessentials.undo

object Undo {


    private val buffer = ArrayList<BEActionBuffer>()

    private var bufferIndex = -1



    fun undo() {
        if(bufferIndex < 0) return

        buffer[bufferIndex].undoAll()

        bufferIndex--
    }



    fun redo() {
        if(bufferIndex < 0 && buffer.size == 0) return

        if(bufferIndex >= buffer.size - 1) return

        bufferIndex++

        buffer[bufferIndex].redoAll()
    }



    fun addBuffer(actionBuffer: BEActionBuffer) {
        /*if(buffer.size != 0)
            for(i in buffer.size - 1 downTo bufferIndex)
                buffer.removeAt(i)

        bufferIndex = buffer.size - 1*/

        //This will not preserve previous action buffers if the buffer index isn't already at the latest.
        if(bufferIndex != buffer.size - 1) {
            buffer.clear()
            bufferIndex = -1
        }

        buffer.add(actionBuffer)

        bufferIndex++
    }


}