package net.flytre.no_angled_brackets.mixin;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.InputStream;
import java.util.Map;
import java.util.function.BiConsumer;

@Mixin(Language.class)
public class LanguageMixin {


    @Inject(method="load", at = @At(value = "INVOKE", target = "Lcom/google/gson/JsonObject;entrySet()Ljava/util/Set;"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void no_angled_brackets$work(InputStream inputStream, BiConsumer<String, String> entryConsumer, CallbackInfo ci, JsonObject jsonObject) {
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            JsonElement value = entry.getValue();
            if(value instanceof JsonPrimitive && ((JsonPrimitive) value).isString())
                jsonObject.addProperty(entry.getKey(),value.getAsString().replace("<%s>", "%s:"));
        }
    }
}
