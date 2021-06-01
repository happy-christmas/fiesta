package xyz.hkdev.mc.fiesta.mixin;

import net.minecraft.crash.CrashReport;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@Mixin(CrashReport.class)
public class MixinCrashReport {

    @Final
    @Shadow
    private String description;

    public String getWittyComment() {
        ArrayList<String> comments = new ArrayList<>();
        comments.add("뭔가 잘못되었다.");
        comments.add("ㅋㅋㅋㅋㅋㅋㅋㅋㅋ");
        comments.add("샌즈가 당신의 마크를 삭제!");
        comments.add("아 그거 그럿개 하는거 아닌대");
        comments.add("쩝");
        return comments.get(new Random().nextInt(comments.size()));
    }

    @Overwrite
    public String getCompleteReport() {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("---- HK Fiesta 충돌 로그 ----\n");
        net.minecraftforge.fml.relauncher.CoreModManager.onCrash(stringbuilder);
        stringbuilder.append("// ");
        stringbuilder.append(getWittyComment());
        stringbuilder.append("\n\n");
        stringbuilder.append("시간: ");
        stringbuilder.append((new SimpleDateFormat()).format(new Date()));
        stringbuilder.append("\n");
        stringbuilder.append("설명: ");
        stringbuilder.append(this.description);
        stringbuilder.append("\n\n");
        stringbuilder.append(this.getCauseStackTraceOrString());
        stringbuilder.append("\n\n오류와 코드 스택 등 모든 세부 정보는 다음과 같습니다:\n");

        for (int i = 0; i < 87; ++i)
        {
            stringbuilder.append("-");
        }

        stringbuilder.append("\n\n");
        this.getSectionsInStringBuilder(stringbuilder);
        return stringbuilder.toString();
    }

    @Shadow
    public String getCauseStackTraceOrString() { return null; }

    @Shadow
    public void getSectionsInStringBuilder(StringBuilder builder) {}

}
