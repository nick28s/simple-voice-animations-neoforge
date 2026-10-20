package de.maxhenkel.simplevoiceanimations.mixin;

import de.maxhenkel.simplevoiceanimations.interfaces.AnimationDataExtension;
import de.maxhenkel.simplevoiceanimations.interfaces.SplitHeadExtension;
import de.maxhenkel.simplevoiceanimations.config.ClientConfig;
import de.maxhenkel.simplevoiceanimations.sync.Preferences;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin {

    private static final float SPLIT_ROTATION = Mth.DEG_TO_RAD * 45F;

    @Shadow
    @Final
    public ModelPart head;

    @Shadow
    @Final
    public ModelPart hat;

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("RETURN"))
    private void simplevoiceanimations$setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (!(entity instanceof AnimationDataExtension dataExtension)) {
            return;
        }
        float progress = dataExtension.simplevoiceanimations$getData().getAnimationProgress();
        Preferences preferences = ClientConfig.getPreferences();
        float animationProgress = progress * preferences.getIntensity();
        boolean split = preferences.getHeadAnimationStyle().hasSplit() && animationProgress > 0.001F;
        applySplit(head, split, animationProgress, preferences.getHeadAnimationStyle(), preferences.getHeadSplitHeight());
        applySplit(hat, split, animationProgress, preferences.getHeadAnimationStyle(), preferences.getHeadSplitHeight());
    }

    private static void applySplit(ModelPart part, boolean split, float progress, ClientConfig.HeadAnimationStyle style, float splitHeight) {
        ((SplitHeadExtension) (Object) part).simplevoiceanimations$setSplitHead(split, splitHeight, style == ClientConfig.HeadAnimationStyle.SPLIT_HINGE ? progress * SPLIT_ROTATION : 0F, style == ClientConfig.HeadAnimationStyle.SPLIT_VERTICAL ? progress * 0.2F : 0F);
    }

}
