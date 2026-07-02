package io.gremstudio.gremlib.block.sign;

import net.minecraft.client.resources.model.Material;

// Code is based on Terraform
// Original found here: https://github.com/TerraformersMC/Terraform/blob/1.19.4/terraform-wood-api-v1/src/main/java/com/terraformersmc/terraform/sign/TerraformSign.java
public interface GremSign {
    //Todo: Is this still needed these days? I dont think so.
    Material getTexture();
}
