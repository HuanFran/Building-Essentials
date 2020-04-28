package com.huanfran.buildingessentials.rendering

object ObservationRenderer : Renderer() {



    /*fun render(stack: MatrixStack, partialTicks: Float, pos0: BlockPos?, pos1: BlockPos?) {
        stack.push()

        //Calculate the player's position and where the player is looking.
        val lookingAt = rayTraceResult(1000.0, partialTicks).pos
        val playerPos = Minecraft.getInstance().gameRenderer.activeRenderInfo.projectedView

        //Updating the lookingAt variable in the item's class. This is used for selecting pos0 and pos1.
        StaffOfObservation.lookingAt = lookingAt

        //Determining to which point the render will be relative.
        val zero: BlockPos = pos0 ?: lookingAt
        stack.translate(zero.x - playerPos.x, zero.y - playerPos.y, zero.z - playerPos.z)

        //Configuring rendering.
        RenderSystem.disableTexture()
        colour(0.8, 0.0, 0.0, 0.6)
        enableTransparency()

        //Store the last matrix from the stack to use in rendering.
        val matrix = stack.last.matrix

        //Offset to avoid overlay issues with the blocks. It also looks fancy.
        val offset = 0.05F

        beginQuads()

        //Always render the zero block, which is either lookingAt or pos0.
       // renderPos(builder, matrix, offset, BlockPos(0,0,0))

        //Render pos1 if it is not null.
       // pos1?.let { renderPos(builder, matrix, offset, it.subtract(pos0!!)) }
        //if(pos0 != null && pos1 == null) renderPos0ToLookingAt(matrix, pos0, lookingAt)
        tessellator.draw()

        //Restore the initial state.
        RenderSystem.enableTexture()
        RenderSystem.enableDepthTest()

        //Restore the stack's state to prevent rendering bugs elsewhere in Minecraft.
        stack.pop()
    }



    fun renderPos0ToLookingAt(matrix: Matrix4f, pos0: BlockPos, lookingAt: BlockPos) {
        val start = pos0.toVector3() + 0.5
        val end = lookingAt.toVector3() + 0.5

        val dif = end - start

        builder.let {
            //it.pos(matrix, BlockPos.ZERO.toVector3())
            //it.pos(matrix, dif)
        }
    }*/



}