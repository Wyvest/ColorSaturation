package org.polyfrost.colorsaturation.mixin;

import cc.polyfrost.oneconfig.libs.universal.UMinecraft;
import net.minecraft.client.renderer.EntityRenderer;
import org.polyfrost.colorsaturation.EntityRendererHook;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "com.tterrag.blur.Blur", remap = false)
public class BlurModMixin {
    @Dynamic("Blur Mod")
    @Redirect(method = "onGuiChange", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;isShaderActive()Z", ordinal = 0, remap = true), remap = false)
    private boolean isShaderActive(EntityRenderer er) {
        if (
                        //#if MC<=11202
                        net.minecraft.client.renderer.OpenGlHelper.shadersSupported
                        //#else
                        //$$ true
                        //#endif
                        && ((EntityRendererHook) UMinecraft.getMinecraft().entityRenderer).colorSaturation$getSaturationShader() != null
        ) {
            return false;
        }
        return er.isShaderActive();
    }
}