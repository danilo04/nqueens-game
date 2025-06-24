#!/bin/bash

# Comprehensive script to run code quality checks
# This can be used manually or by CI/CD

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
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

# Function to check if gradlew exists
check_gradlew() {
    if [ ! -f "./gradlew" ]; then
        print_error "gradlew not found. Please make sure you're in the project root."
        exit 1
    fi
}

# Function to run ktlint check
run_ktlint_check() {
    print_status "Running Ktlint check..."
    if ./gradlew ktlintCheck; then
        print_success "Ktlint check passed!"
        return 0
    else
        print_error "Ktlint check failed!"
        print_warning "Run './gradlew ktlintFormat' to auto-fix formatting issues."
        return 1
    fi
}

# Function to run ktlint format
run_ktlint_format() {
    print_status "Running Ktlint format..."
    if ./gradlew ktlintFormat; then
        print_success "Code formatted successfully!"
        return 0
    else
        print_error "Ktlint format failed!"
        return 1
    fi
}

# Function to run detekt
run_detekt() {
    print_status "Running Detekt analysis..."
    if ./gradlew detekt; then
        print_success "Detekt analysis passed!"
        return 0
    else
        print_error "Detekt analysis failed!"
        print_warning "Check the report at: app/build/reports/detekt/detekt.html"
        return 1
    fi
}

# Function to run all quality checks
run_quality_checks() {
    print_status "Running comprehensive code quality checks..."
    
    local ktlint_result=0
    local detekt_result=0
    
    run_ktlint_check || ktlint_result=1
    run_detekt || detekt_result=1
    
    if [ $ktlint_result -eq 0 ] && [ $detekt_result -eq 0 ]; then
        print_success "All code quality checks passed! 🎉"
        return 0
    else
        print_error "Some code quality checks failed."
        return 1
    fi
}

# Function to show help
show_help() {
    echo "Code Quality Check Script"
    echo ""
    echo "Usage: $0 [OPTION]"
    echo ""
    echo "Options:"
    echo "  check      Run Ktlint and Detekt checks (default)"
    echo "  format     Format code with Ktlint"
    echo "  ktlint     Run only Ktlint check"
    echo "  detekt     Run only Detekt analysis"
    echo "  help       Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0              # Run all checks"
    echo "  $0 check        # Run all checks"
    echo "  $0 format       # Format code"
    echo "  $0 ktlint       # Run only Ktlint"
    echo "  $0 detekt       # Run only Detekt"
}

# Main execution
main() {
    check_gradlew
    
    case "${1:-check}" in
        "check")
            run_quality_checks
            ;;
        "format")
            run_ktlint_format
            ;;
        "ktlint")
            run_ktlint_check
            ;;
        "detekt")
            run_detekt
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
