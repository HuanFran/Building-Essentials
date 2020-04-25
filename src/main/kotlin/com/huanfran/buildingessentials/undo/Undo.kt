package com.huanfran.buildingessentials.undo

/**
 * Contains the necessary variables and functions to make undo and redo operations possible in the Building Essentials
 * mod.
 */
object Undo {


    /**
     * A list of all recent [BEActionBuffer]s that have been created. On initialisation, [BEActionBuffer]s are
     * automatically added to this list.
     */
    private val buffer = ArrayList<BEActionBuffer>()

    /**
     * The index of the [buffer] at which the undo-redo state is currently. Moving this backwards or forwards has the
     * effect of undoing or redoing based on the [BEActionBuffer] of the index in the [buffer] list.
     */
    private var bufferIndex = -1



    /**
     * Undoes a [BEActionBuffer] operation. This decreases the [bufferIndex].
     */
    fun undo() {
        if(bufferIndex < 0) return

        buffer[bufferIndex].undoAll()

        bufferIndex--
    }



    /**
     * Redoes a [BEActionBuffer] operation. This increases the [bufferIndex].
     */
    fun redo() {
        if(bufferIndex < 0 && buffer.size == 0) return

        if(bufferIndex >= buffer.size - 1) return

        bufferIndex++

        buffer[bufferIndex].redoAll()
    }



    /**
     * Adds the given [actionBuffer] to the [buffer] list. The buffer index is increased. If the [bufferIndex] is not
     * at the most recently added [BEActionBuffer], then the entire [buffer] list is cleared and the [bufferIndex] is
     * reset. I.e. if the user has undone some actions, making some new actions (e.g. using the staff of extension) will
     * render any previous actions completed and unable to be undone.
     */
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