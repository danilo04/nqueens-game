#!/bin/bash

# Script to update Detekt configuration and check for deprecated properties
# Run this script when upgrading Detekt versions

set -e

echo "🔍 Checking Detekt configuration for deprecated properties..."

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

print_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Function to generate default config for comparison
generate_default_config() {
    print_info "Generating default Detekt configuration for comparison..."
    if ./gradlew detektGenerateConfig; then
        print_success "Default config generated at: app/build/reports/detekt/config.yml"
        print_info "You can compare this with your current config at: app/config/detekt/detekt.yml"
        return 0
    else
        print_error "Failed to generate default config"
        return 1
    fi
}

# Function to validate current config
validate_config() {
    print_info "Validating current Detekt configuration..."
    if ./gradlew detekt --dry-run; then
        print_success "Detekt configuration is valid!"
        return 0
    else
        print_error "Detekt configuration has issues. Check the output above."
        return 1
    fi
}

# Function to show help
show_help() {
    echo "Detekt Configuration Helper Script"
    echo ""
    echo "Usage: $0 [OPTION]"
    echo ""
    echo "Options:"
    echo "  validate       Validate current Detekt configuration (default)"
    echo "  generate       Generate default config for comparison"
    echo "  both           Run both validation and generation"
    echo "  help           Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0              # Validate current config"
    echo "  $0 validate     # Validate current config"
    echo "  $0 generate     # Generate default config"
    echo "  $0 both         # Run both operations"
}

# Main execution
main() {
    case "${1:-validate}" in
        "validate")
            validate_config
            ;;
        "generate")
            generate_default_config
            ;;
        "both")
            validate_config
            echo ""
            generate_default_config
            ;;
        "help"|"-h"|"--help")
            show_help
            ;;
        *)
            print_error "Unknown option: $1"
            show_help
            exit 1
            ;;
    esac
}

main "$@"
