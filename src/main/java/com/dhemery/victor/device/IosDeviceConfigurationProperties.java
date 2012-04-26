package com.dhemery.victor.device;

public class IosDeviceConfigurationProperties {
    /**
     * The path to the iOS application binary file to execute.
     * This is typically a file inside the application's .app package.
     * The file's executable flag must be set.
     */
    public static final String APPLICATION_BINARY_PATH = "victor.application.binary.path";
    /**
     * The path to the root directory of the iOS SDK with which to launch the simulator.
     */
    public static final String SDK_ROOT = "victor.sdk.root";
    /**
     * The path to the iOS Simulator executable file.
     */
    public static final String SIMULATOR_BINARY_PATH = "victor.simulator.binary.path";
}