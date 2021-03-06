package com.huanfran.buildingessentials.graphics.gl.enumeration

import com.huanfran.buildingessentials.graphics.gl.enumeration.identification.GLParameter
import org.lwjgl.opengl.GL46C

enum class ShaderProgramParameter(val id: Int) : GLParameter {


    DELETE_STATUS(GL46C.GL_DELETE_STATUS),

    LINK_STATUS(GL46C.GL_LINK_STATUS),

    VALIDATE_STATUS(GL46C.GL_VALIDATE_STATUS),

    INFO_LOG_LENGTH(GL46C.GL_INFO_LOG_LENGTH),

    ATTACHED_SHADERS(GL46C.GL_ATTACHED_SHADERS),

    ACTIVE_ATTRIBUTES(GL46C.GL_ACTIVE_ATTRIBUTES),

    ACTIVE_ATTRIBUTE_MAX_LENGTH(GL46C.GL_ACTIVE_ATTRIBUTE_MAX_LENGTH),

    ACTIVE_UNIFORMS(GL46C.GL_ACTIVE_UNIFORMS),

    ACTIVE_UNIFORM_MAX_LENGTH(GL46C.GL_ACTIVE_UNIFORM_MAX_LENGTH),

    TRANSFORM_FEEDBACK_BUFFER_MODE(GL46C.GL_TRANSFORM_FEEDBACK_BUFFER_MODE),

    TRANSFORM_FEEDBACK_VARYINGS(GL46C.GL_TRANSFORM_FEEDBACK_VARYINGS),

    TRANSFORM_FEEDBACK_VARYING_MAX_LENGTH(GL46C.GL_TRANSFORM_FEEDBACK_VARYING_MAX_LENGTH),

    ACTIVE_UNIFORM_BLOCKS(GL46C.GL_ACTIVE_UNIFORM_BLOCKS),

    ACTIVE_UNIFORM_BLOCK_MAX_NAME_LENGTH(GL46C.GL_ACTIVE_UNIFORM_BLOCK_MAX_NAME_LENGTH),

    GEOMETRY_VERTICES_OUT(GL46C.GL_GEOMETRY_VERTICES_OUT),

    GEOMETRY_INPUT_TYPE(GL46C.GL_GEOMETRY_INPUT_TYPE),

    GEOMETRY_OUTPUT_TYPE(GL46C.GL_GEOMETRY_OUTPUT_TYPE),

    PROGRAM_BINARY_LENGTH(GL46C.GL_PROGRAM_BINARY_LENGTH),

    ACTIVE_ATOMIC_COUNTER_BUFFERS(GL46C.GL_ACTIVE_ATOMIC_COUNTER_BUFFERS),

    COMPUTE_WORK_GROUP_SIZE(GL46C.GL_COMPUTE_WORK_GROUP_SIZE);


}