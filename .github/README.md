# GitHub Actions CI/CD Setup

This project includes comprehensive GitHub Actions workflows for automated testing, code quality checks, and releases.

## 🚀 Workflows Overview

### 1. Main CI Pipeline (`ci.yml`)
**Triggers:** Push to `main`/`develop`, Pull Requests
- **Code Quality**: Runs Ktlint and Detekt analysis
- **Unit Tests**: Executes all unit tests with JUnit
- **Android Tests**: Runs instrumentation tests on Android emulator
- **Build**: Creates debug APK
- **Artifacts**: Uploads reports and APK files

### 2. Pull Request Quality Check (`pr-quality-check.yml`)
**Triggers:** Pull Requests to `main`/`develop`
- Comprehensive quality gate with detailed reporting
- Automatic PR comments with quality metrics
- Detekt analysis summary
- Test failure details
- Quality gate pass/fail status

### 3. Security & Dependency Check (`security-check.yml`)
**Triggers:** Weekly schedule (Sundays 2 AM UTC), Manual dispatch
- Scans for dependency updates
- Security vulnerability checks
- Automatic issue creation for updates needed
- Dependency update reports

### 4. Release Workflow (`release.yml`)
**Triggers:** Git tags (`v*`), Manual dispatch
- Pre-release quality gate (all checks must pass)
- Builds release APK
- Creates GitHub release with changelog
- Uploads signed APK (if keystore configured)

## 📋 Quality Checks Included

### Code Quality
- ✅ **Ktlint**: Kotlin code formatting
- ✅ **Detekt**: Static code analysis
- ✅ **Custom quality script**: `./code-quality.sh`

### Testing
- ✅ **Unit Tests**: JUnit with Mockito
- ✅ **Android Instrumentation Tests**: UI and integration tests
- ✅ **Test Reports**: Automatic upload and GitHub integration

### Security
- ✅ **Dependency Scanning**: Weekly vulnerability checks
- ✅ **Update Notifications**: Automatic issue creation
- ✅ **Gradle Security**: Wrapper validation

## 🛠️ Setup Requirements

### Required Secrets (for release workflow)
If you want to sign your release APKs, add these secrets to your repository:

```
KEYSTORE_FILE          # Base64 encoded keystore file
KEYSTORE_PASSWORD      # Keystore password
KEY_ALIAS             # Key alias name
KEY_PASSWORD          # Key password
```

### Permissions
The workflows require the following GitHub token permissions:
- `contents: read/write` (for code access and releases)
- `issues: write` (for creating dependency update issues)
- `pull-requests: write` (for PR comments)

## 📊 Artifacts & Reports

### Automatically Generated
- **Test Reports**: JUnit XML and HTML reports
- **Code Quality Reports**: Detekt HTML/XML/SARIF reports
- **APK Files**: Debug and release builds
- **Dependency Reports**: Security and update information

### Report Locations
- Test reports: `app/build/reports/tests/`
- Detekt reports: `app/build/reports/detekt/`
- APK outputs: `app/build/outputs/apk/`

## 🎯 Quality Gates

### Pull Request Requirements
For a PR to pass the quality gate:
1. ✅ All Ktlint checks must pass
2. ✅ All Detekt rules must pass
3. ✅ All unit tests must pass
4. ✅ Code coverage meets standards

### Release Requirements
For a release to be created:
1. ✅ All quality checks must pass
2. ✅ All tests must pass
3. ✅ Static analysis must be clean
4. ✅ Security scans must pass

## 🔧 Local Development

### Run Quality Checks Locally
```bash
# Run all quality checks
./code-quality.sh check

# Format code
./code-quality.sh format

# Run specific checks
./code-quality.sh ktlint
./code-quality.sh detekt
```

### Run Tests Locally
```bash
# Unit tests
./gradlew test

# Android tests (requires emulator/device)
./gradlew connectedAndroidTest

# All tests
./gradlew check
```

### Manual Workflow Triggers

You can manually trigger workflows from the GitHub Actions tab:
- **Security Check**: For immediate dependency scanning
- **Release**: To create a release without pushing a tag

## 📈 Monitoring & Notifications

### Pull Request Comments
- Automatic quality report comments on PRs
- Test failure summaries
- Detekt analysis results
- Pass/fail status indicators

### Issue Creation
- Weekly dependency update notifications
- Security vulnerability alerts
- Automated issue labeling and tracking

## 🎨 Customization

### Modify Quality Standards
- Edit `app/config/detekt/detekt.yml` for Detekt rules
- Modify `app/build.gradle.kts` for Ktlint configuration
- Update `code-quality.sh` for custom checks

### Add New Workflows
- Copy existing workflow structure
- Follow naming convention: `snake-case.yml`
- Add appropriate triggers and jobs
- Document in this README

## 🐛 Troubleshooting

### Common Issues
1. **Gradle Permission Denied**: Ensure `gradlew` is executable
2. **Android Emulator Fails**: Check API level compatibility
3. **Quality Checks Fail**: Run `./code-quality.sh format` locally
4. **Tests Timeout**: Increase timeout in workflow files

### Getting Help
- Check workflow logs in GitHub Actions tab
- Review quality reports in uploaded artifacts
- Run checks locally to debug issues
- Consult individual tool documentation:
  - [Detekt](https://detekt.dev/)
  - [Ktlint](https://ktlint.github.io/)
  - [Android Testing](https://developer.android.com/training/testing)

---

*This CI/CD setup ensures high code quality and reliable releases for the N-Queens Game project.*
