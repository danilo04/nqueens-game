#!/bin/bash

# Script to setup pre-commit hook for Detekt and Ktlint
# Run this script from the root of your project

set -e

echo "Setting up pre-commit hook for Detekt and Ktlint..."

# Check if .git directory exists
if [ ! -d ".git" ]; then
    echo "Error: This is not a Git repository. Please run 'git init' first."
    exit 1
fi

# Create .git/hooks directory if it doesn't exist
mkdir -p .git/hooks

# Create the pre-commit hook
cat > .git/hooks/pre-commit << 'EOF'
#!/bin/bash

# Pre-commit hook for Detekt and Ktlint
# This hook runs Detekt and Ktlint on modified Kotlin files

set -e

echo "Running pre-commit checks..."

# Get list of modified Kotlin files
MODIFIED_FILES=$(git diff --cached --name-only --diff-filter=ACM | grep -E '\.(kt|kts)$' || true)

if [ -z "$MODIFIED_FILES" ]; then
    echo "No Kotlin files modified, skipping checks."
    exit 0
fi

echo "Modified Kotlin files:"
echo "$MODIFIED_FILES"

# Function to check if gradlew exists
check_gradlew() {
    if [ ! -f "./gradlew" ]; then
        echo "Error: gradlew not found. Please make sure you're in the project root."
        exit 1
    fi
}

# Function to run ktlint check
run_ktlint() {
    echo "Running Ktlint check..."
    if ! ./gradlew ktlintCheck; then
        echo "❌ Ktlint check failed!"
        echo "Run './gradlew ktlintFormat' to auto-fix formatting issues, then commit again."
        exit 1
    fi
    echo "✅ Ktlint check passed!"
}

# Function to run detekt
run_detekt() {
    echo "Running Detekt check..."
    if ! ./gradlew detekt; then
        echo "❌ Detekt check failed!"
        echo "Please fix the issues reported by Detekt and commit again."
        echo "Check the report at: app/build/reports/detekt/detekt.html"
        exit 1
    fi
    echo "✅ Detekt check passed!"
}

# Main execution
check_gradlew

# Run ktlint first (it can auto-fix issues)
run_ktlint

# Run detekt
run_detekt

echo "🎉 All pre-commit checks passed!"
EOF

# Make the pre-commit hook executable
chmod +x .git/hooks/pre-commit

echo "✅ Pre-commit hook has been successfully installed!"
echo ""
echo "The hook will run Ktlint and Detekt on modified Kotlin files before each commit."
echo ""
echo "To manually run the checks:"
echo "  - Ktlint check: ./gradlew ktlintCheck"
echo "  - Ktlint format: ./gradlew ktlintFormat"
echo "  - Detekt: ./gradlew detekt"
echo ""
echo "To skip the pre-commit hook (not recommended): git commit --no-verify"
