package uk.ac.york.eng2.cli;

import io.micronaut.configuration.picocli.PicocliRunner;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "trending-cli", description = "...",
	subcommands= {
			GetAllHashtagsCommand.class,
			AddHashtagsCommand.class,
	},
        mixinStandardHelpOptions = true)
public class TrendingCliCommand implements Runnable {

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(TrendingCliCommand.class, args);
    }

    public void run() {
        // business logic here
        if (verbose) {
            System.out.println("Hi!");
        }
    }
}
