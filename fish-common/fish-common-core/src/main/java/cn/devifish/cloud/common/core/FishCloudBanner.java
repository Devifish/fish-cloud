package cn.devifish.cloud.common.core;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.core.env.Environment;

import java.io.PrintStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * FishCloudBanner
 *
 * @author Devifish
 * @date 2020/7/2 11:24
 */
public class FishCloudBanner implements Banner {

    private static final String[] BANNER = {
            "    _______      __       ________                __",
            "   / ____(_)____/ /_     / ____/ /___  __  ______/ /",
            "  / /_  / / ___/ __ \\   / /   / / __ \\/ / / / __  / ",
            " / __/ / (__  ) / / /  / /___/ / /_/ / /_/ / /_/ /  ",
            "/_/   /_/____/_/ /_/   \\____/_/\\____/\\__,_/\\__,_/   "
    };

    private static final String SPRING_BOOT = "Power by SpringBoot (v%s)";

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream printStream) {
        int strap_line_size = BANNER[BANNER.length - 1].length();

        String banner = String.join("\n", BANNER);
        String version = String.format(SPRING_BOOT, SpringBootVersion.getVersion());
        String padding = Stream.generate(() -> " ")
                .limit(strap_line_size - version.length())
                .collect(Collectors.joining());

        // 输出到控制台
        printStream.println(banner);
        printStream.println(AnsiOutput.toString(
                AnsiColor.DEFAULT, padding,
                AnsiColor.GREEN, AnsiStyle.FAINT, version));
        printStream.println();
    }
}
