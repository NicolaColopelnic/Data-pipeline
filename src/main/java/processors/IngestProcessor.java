package processors;

import mediator.Mediator;
import state.Event;
import java.io.*;
import java.security.MessageDigest;
import java.util.HexFormat;

// validate checksum and format

public class IngestProcessor extends Processor {

    public IngestProcessor() {
        super("Phase 1: Ingesting");
    }

    @Override
    public void execute(String filePath, Mediator mediator) {
        System.out.println(phaseName + "...");

        try {
            File file = new File(filePath);

            if (file.exists()) {
                String checksum = calculateChecksum(filePath);
                System.out.println("  Integrity check: Hash generated [" + checksum.substring(0, 8) + "...]");
                validateFormat(filePath);
                System.out.println("  File successfully ingested and validated.");
            } else {
                System.err.println("  Error: Physical file '" + filePath + "' not found.");
                System.exit(1);
            }

            // notify the mediator
            mediator.onProcessorSuccess(Event.VALIDATE, "Ingest Phase Complete.");

        } catch (Exception e) {
            System.err.println("  Ingesting Phase Failed: " + e.getMessage());
            System.exit(1);
        }
    }

    private String calculateChecksum(String filepath) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        try (InputStream is = new FileInputStream(filepath)) {
            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = is.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
        }

        byte[] digest = md.digest();
        return HexFormat.of().formatHex(digest);
    }

    private void validateFormat(String filepath) {
        if (filepath.toLowerCase().endsWith(".mp4") || filepath.toLowerCase().endsWith(".mkv")) {
            System.out.println("  Format Validator: Matches studio specs.");
        } else {
            throw new RuntimeException("Unsupported file format: " + filepath);
        }
    }
}