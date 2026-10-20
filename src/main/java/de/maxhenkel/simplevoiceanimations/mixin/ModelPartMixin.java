package de.maxhenkel.simplevoiceanimations.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.maxhenkel.simplevoiceanimations.interfaces.SplitHeadExtension;
import net.minecraft.client.model.geom.ModelPart;
import de.maxhenkel.simplevoiceanimations.SplitHeadRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ModelPart.class)
public class ModelPartMixin implements SplitHeadExtension {

    @Shadow
    @Final
    private List<ModelPart.Cube> cubes;

    @Unique
    private boolean simplevoiceanimations$splitHead;
    @Unique
    private float simplevoiceanimations$splitHeight;
    @Unique
    private float simplevoiceanimations$splitRotation;
    @Unique
    private float simplevoiceanimations$splitDistance;

    @Override
    public void simplevoiceanimations$setSplitHead(boolean split, float height, float rotation, float distance) {
        simplevoiceanimations$splitHead = split;
        simplevoiceanimations$splitHeight = height;
        simplevoiceanimations$splitRotation = rotation;
        simplevoiceanimations$splitDistance = distance;
    }

    @Inject(method = "compile", at = @At("HEAD"), cancellable = true)
    private void simplevoiceanimations$compile(PoseStack.Pose pose, VertexConsumer buffer, int lightCoords, int overlayCoords, int color, CallbackInfo ci) {
        if (!simplevoiceanimations$splitHead) {
            return;
        }
        ci.cancel();
        SplitHeadRenderer.render(cubes, pose, buffer, lightCoords, overlayCoords, color, simplevoiceanimations$splitHeight, simplevoiceanimations$splitRotation, simplevoiceanimations$splitDistance);
    }

}
