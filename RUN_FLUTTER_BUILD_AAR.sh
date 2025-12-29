#!/bin/bash

# Script to build Flutter AAR with POM files
# Run this from your local machine where Flutter SDK is installed

set -e

echo "Building Flutter AAR with POM files..."
echo ""

# Navigate to Flutter module directory
cd ~/weather_module

# Check if Flutter is available
if ! command -v flutter &> /dev/null; then
    echo "ERROR: Flutter command not found!"
    echo "Please ensure Flutter SDK is installed and added to PATH"
    echo ""
    echo "To add Flutter to PATH temporarily:"
    echo "  export PATH=\"\$PATH:/path/to/flutter/bin\""
    echo ""
    exit 1
fi

echo "Flutter version:"
flutter --version | head -3
echo ""

echo "Cleaning previous builds..."
flutter clean
echo ""

echo "Building AAR for all variants (debug, profile, release)..."
flutter build aar
echo ""

echo "Checking generated Maven repository..."
if [ -d "build/host/outputs/repo" ]; then
    echo "✅ Maven repository created successfully!"
    echo ""
    echo "Repository location:"
    echo "  $(pwd)/build/host/outputs/repo"
    echo ""
    echo "Generated artifacts:"
    find build/host/outputs/repo -name "*.aar" -o -name "*.pom" | sort
    echo ""
    echo "POM file locations:"
    find build/host/outputs/repo -name "*.pom" | sort
    echo ""
    echo "✅ Build complete! You can now sync Gradle in Android Studio."
else
    echo "❌ ERROR: Maven repository not found after build"
    exit 1
fi

