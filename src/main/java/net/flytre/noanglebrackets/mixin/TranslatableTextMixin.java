package net.flytre.noanglebrackets.mixin;

import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TranslatableText.class)
public class TranslatableTextMixin {

    @Mutable
    @Shadow @Final private String key;

    @Inject(method = "<init>(Ljava/lang/String;)V", at = @At("TAIL"))
    public void modify(String key, CallbackInfo ci) {
        fixKey();
    }

    @Inject(method = "<init>(Ljava/lang/String;[Ljava/lang/Object;)V", at = @At("TAIL"))
    public void modifyWithArgs(String key, Object[] args, CallbackInfo ci) {
        fixKey();
    }

    public void fixKey() {
        switch(key) {
            case "chat.type.text":
            case "chat.type.emote":
            case "chat.type.announcement":
            case "chat.type.admin":
            case "chat.type.team.text":
            case "chat.type.team.sent":
                key = "custom." + key;
            break;
        }

    }
}
