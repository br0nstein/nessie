# Releases

**See [Nessie Server upgrade notes](server-upgrade.md) for supported upgrade paths.**

## 0.60.1 Release (May 25, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.60.1).

* Release-publish: fix after removed native images (#6898)

## 0.60.0 Release (May 25, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.60.0).

* Pass content-IDs of all `ContentKey` elements to `Check`s (#6859)
* Nit: Spec version updates (#6897)
* Fix `MergeKeyBehavior.DROP` in new storage model for merge (#6894)
* Defer API compatibility check until first request (#6893)
* Remove mentions of spark2 in Docs (#6762)
* Revert "Bump keykloak container image from 21.0.2 to 21.1 (#6887)" (#6895)
* Table renames: allow delete-op after put-op (#6892)
* Bump keykloak container image from 21.0.2 to 21.1 (#6887)
* Update dependency ch.qos.logback:logback-classic to v1.2.12 (#6889)
* Bugfix: include ROCKSDB in selector to create PVC in both cases. (#6881)
* Build: cleanup `libs.versions.toml` (#6886)
* Don't use filters when checking API compatibility (#6877)
* Make nip.io usage resilient against lookup failures (#6885)
* CI/Nesqueit: Switch back to `iceberg-nesqueit` branch (#6883)
* Ability to disable the API compatibility check via system properties (#6875)
* Skip API compatibility check if /config endpoint fails (#6878)
* Fix snapshot publising after #6847 (#6882)
* Build: minor `baselibs.versions.toml` update (#6884)
* Improve NessieError message (#6874)
* Disable Scylla tests on macOS (#6871)
* Enable compatibility tests on macOS (#6857)
* Use nip.io domain in MinioExtension (#6856)
* Docker compose template for Nessie + OpenTelemetry (#6860)
* Release: Remove relocation-poms (#6810)
* Remove native image (#6847)
* Use API V2 by default in GC Tool (#6858)
* Enables the extended information in `NessieConfiguration` (#6640)
* Add missing jakarta annotations (#6850)
* Nit: Remove unneeded dev-profile hints (#6851)
* Quarkus ITs: Restrict Keycloak to tests using Keycloak (#6852)

## 0.59.0 Release (May 18, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.59.0).

* Add an API compatibility check to Nessie clients (#6818)
* Remove Content type hierarchy from Events API (#6840)
* Mark REST API v2 as GA (#6749)
* REST V2: Key ranges for entries + diff (#6743)
* Add ObjId.objIdFromByteBuffer() (#6845)
* Wikis / REST API v2 changes (#6801)
* Allow exporting only the latest contents from a branch. (#6823)
* Events notification system for Nessie - Service module (#6760)
* Remove the lambda module (#6819)
* increase severity of multiple errorprone checks (#6800)
* Tests: Add utility check for new storage model (#6814)
* Nit: let export/new-model not use a deprecated function (#6813)
* Release: no relocation-pom for events (#6809)
* SQL Extensions: Fix handling of quoted reference names (#6804)
* Update site with new storage config (#6795)
* UI: Handle reference names with `/` (#6806)
* set ClassCanBeStatic severity to ERROR (#6797)
* Some errorprone mitigations (no functional change) (#6796)
* Make EventSubscriber.onSubscribe not default (#6777)
* Make Reference.getFullName return Optional<String> (#6776)
* Add new content type UDF (#6761)
* Add missing test for #6758 (#6768)
* Events: align field names to model (#6771)
* Blog: Fix rendering of namespace creation option (#6778)
* Blog: Update about namespace enforcement (#6753)
* Add Alex to dev list (#6775)
* New storage: allow deletion of multiple repositories (#6758)
* Fix left-over TODO from content-metadata PR (#6759)
* Events notification system for Nessie - VersionStore changes (#6647)
* Nessie error details: Add `ContentKey` to related errors (#6721)
* Minor optimization when retrieving named referencs (#6745)
* Cleanup V2 MergeResponse (#6747)
* GC: Trim long failure messages in JDBC repo (#6746)
* Nit: remove no longer valid TODO (#6744)
* Add extraEnv to helm chart (#6698)
* Events notification system for Nessie - SPI module (#6733)
* CI/main: Run CI on main sequentially (#6741)
* CI: Run "forgotten" java 8 tests (#6738)
* Bump json5 to 2.2.3 (#6739)
* Revert "fix(deps): update mockito monorepo to v5 (major) (#6731)" (#6737)
* Reserve usage of BatchingPersist to dry-run mode only (#6736)
* UI: Update a bunch of JS dependencies (#6734)
* Nit: Remove unnecessary `JdkSpecifics` (#6729)
* Events notification system for Nessie - API module (#6646)
* Build/CI: pass `test.log.level` via `CommandLineArgumentProvider` and populate Quarkus console log level (#6725)
* Remove top-level condition from the `CI Website` job (#6728)
* CI: add `build/quarkus.log` to failed quarkus jobs artifacts (#6727)
* Do not store intermediate commits during merge/transplant (#6677)
* build: `buildSrc` using Java toolchain (#6726)
* Cassandra: add timeouts for DDL + DML (#6716)
* Keycloak requires container-network (#6719)
* CI: update helm-chart-testing (#6720)
* Nit: remove unused version definition (#6718)
* CI: capture test reports (#6717)
* Let `:nessie-versioned-spi` use OpenTelemetry (#6687)
* Minor delta test fix (#6699)
* Add Nessie spec definition for 2.0.0-beta.1 (#6679)
* Change default message for (squash) merges (#6642)
* Add "fallback cases" for relevant enums in `:nessie-model` (#6634)
* Make dry run merge / transplant throw exceptions (#6685)
* Rename `ConflictResolution.IGNORE` -> `ADD` (#6686)
* Add more commit-attributes to `Merge` (#6641)
* Implement "external" conflict resolution for merges (#6631)
* Ability to pass advanced config as nested YAML (#6684)
* Remove unnecessary annotations from `ITImplicitNamespaces` (#6678)
* Ensure custom content-types work (#6618)
* Nessie: Generic information for operations and content results (#6616)
* Events design doc - minor evolutions (#6672)
* bugfix: namespaceDepth filter loses entry content (#6648)
* Expose Nessie repository information (preparation) (#6635)
* Allow all commit attributes for namespace operations (#6643)
* Mark `namedRef` as `@Nullable` in `ContentService` (#6638)
* Allow Nessie clients to deserialize unknown `Content.Type`s (#6633)
* Prepare REST API for content-aware merges (#6619)

## 0.58.1 Release (April 19, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.58.1).

* Null out references to java.net.http.HttpClient (#6630)
* Validate that the generated `nessie-gc` executable works (#6625)
* Cleanup content-type code (#6617)
* Add a test for getContent() on the default branch (API v1) (#6623)
* Tests for the OAuth2 authentication provider in nessie-quarkus (#6597)
* Add test for specVersion in API v2 (#6599)
* Disable IT-Auth on WIn/Mac CI (#6615)
* Docs for the new OAuth2 authentication provider (#6595)

## 0.58.0 Release (April 15, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.58.0).

* Renovate: let more dependency trigger Quarkus/native CI (#6596)
* OAuth2 authentication provider for Nessie client (#6527)
* Design document for Nessie events notification system (#6482)
* JAX-RS: Properly "pass through" `WebApplicationException` (#6584)
* CI: Make Quarkus/native job not a matrix job (#6589)
* Quarkus: Fix HTTP compression parameters (#6581)
* Remove direct use of hamcrest (#6583)
* CI: Replace auto-labeler with changed-files checks (#6577)
* CI: Use Java 20 in "newer-java" (#6582)
* Update Helm template for new storage implementation. (#6580)
* Gradle: eliminate some more `doFirst`/`doLast` script references (#6516)
* CI: Remove `CI Success` job (#6572)
* Align Quarkus dependencies (#6565)
* CI: Skip Helm CI, if version is not available (fix) (#6559)
* Revert "Update smartbear/swaggerhub-cli action to v0.7.1 (#6519)" (#6556)
* Release-WF: Disable the Gradle cache (#6555)

## 0.57.0 Release (April 11, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.57.0).

* CI: adjust Gradle cache storage parameters for non-`ci.yml` workflows (#6521)
* Build: use Smallrye-OpenAPI-Plugin from Gradle plugins (#6525)
* Perftest: Fix commit-to-branch simulation (#6552)
* Support `@NessieServerProperty` annotations in nested tests (#6548)
* Remove unused type parameter in Backends.createPersist (#6554)
* CI: Skip Helm CI, if version is not available (#6546)
* Fix wrong jackson-core coordinates (#6553)
* CI: Un-bump latest Java from 20 to 19 (#359) (#6551)
* CI-Workflow updates (#6481)
* CI: finally the correct artifacts (#6520)
* README: Fix deep-links into CI jobs (#6522)
* Bump nodejs to 18.15.0 LTS + npm to 9.5.0 LTS (#6542)
* Merge: Verify that there is at least one source commit (#6514)
* Gradle: minor non-changes for configuration-cache (#6513)
* CI: Cache everything that's needed (#6515)
* Fix `nessie-gc` publication (#6511)
* Fix specVersion to comply with semver syntax (#6506)

## 0.56.0 Release (April 05, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.56.0).

* CI: Save 15m in CI NesQuEIT (#6505)
* Populate reference conflict details (#6503)
* Enhance `NessieError` with error details (#6492)
* Revert slf4j2 bump (#6504)
* Revert "Update smartbear/swaggerhub-cli action to v0.7.0 (#6478)" (#6500)
* Test code cleanup / no functional change (#6502)
* Add `@JsonView` to HttpConfigApi v2 (#6498)
* Fix CI badge on README.md (#6499)

## 0.55.0 Release (April 04, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.55.0).

* Add min-version + spec-version-bag to `NessieConfiguration` (#6480)
* IntelliJ: Exclude build directories in build-tools-integration-tests (#6489)
* Support running compatibility tests with the new data model. (#6484)
* Test code: Refactor `Json` to `JsonNode` in tests (#6487)
* Bump jaxb-impl from hadoop-common (#6485)
* Let errorprone not check code generated by APTs (#6486)
* Bring back logback logging (#6469)
* Remove concurrent test case execution per class (#6479)
* WF: No more need to schedule contaner-registry sync (#6454)
* Refactor CI workflow - bring back reasonable CI runtime (#6461)
* Do not let Weld register a shutdown hook (#6470)
* Skip empty commits during merge/transplant (#6468)
* Simplify BaseCommitHelper.mergeSquashFastForward() (#6467)
* Nit: remove unused version decl (#6466)
* Fix validation error messages in CommitImpl (#6450)
* Add a convenience `compileAll` Gradle task (#6456)
* Fix nessie-perftest-simulations standalone (#6453)
* IntelliJ: Let IntelliJ test-runner default to CHOOSE_PER_TEST again (#6452)

## 0.54.0 Release (March 30, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.54.0).

* Scylla: 8->4G (#6446)
* Remove the need to pass `Put.expectedContent()` (#6438)
* Tests: Faster ScyllaDB container startup (#6444)
* IntelliJ: Always use Gradle to run tests (#6445)
* Testing: Faster C* container startup (#6443)
* Re-add Cassandra version store type to Nessie-Quarkus (#6440)
* Allow CI on Nessie forks (#6442)
* Cleanup/build: Remove no longer used Gradle configuration (#6437)
* Add missing "project only" code-coverage-report tasks (#6436)
* Fix OOM when running tests `:nessie-cli` (#6435)
* Some minor Gradle build optimizations (#6428)
* Testing: add `-XX:+HeapDumpOnOutOfMemoryError` (#6433)
* Nit: fix IntelliJ import warning for nessie-perftest-simulations (#6431)
* Disable testcontainers startup checks (#6423)
* Remove old content-attachments approach (#6422)
* Expand iceberg-views version-ID to 64 bit (#6421)
* Update Nessie client version as per Iceberg 1.2.0 release (#6412)
* Site: Update Iceberg, Flink, Presto versions (#6419)
* Gradle CI tweaks (#6416)
* Bump maven to 3.9.1 + maven-resolver to 1.9.7 (#6413)
* Nit: suppress "unclosed resource" warning in some tests (#6408)
* UI-Build: Use openapi-generator jar directly (#6411)
* Nit: remove unnecessary `.collect()` (#6414)
* Nit: Suppress a bunch of unchecked and deprecated warnings (#6410)
* Fix nullability for fields of ContentValueObj (#6409)
* Remove deprecated and unused `@NessieUri` annotation (#6407)
* Tests: Update Quarkus' TestNessieError (#6399)
* Remove exception mapping for `java.security.AccessControlException` (#6405)
* Nit: remove unused code from #6384 (#6406)

## 0.53.1 Release (March 24, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.53.1).

* New storage: add "batch write" facade (#6385)
* Persist: replace `updateObj()` with `upsertObj()` (#6384)
* Import: print total duration (#6383)
* IntelliJ: exclude more directories from indexing (#6374)
* Import: Eager prefetching of commits during finalization (#6378)
* New storage: `Persist.updateObj()` must respect index-size limits (#6377)
* Import: use updated commit as new parent (#6376)
* Nessie Import: Respect commit import batch size (new model) (#6375)
* Nessie Import: Print durations, double row size printed to console (#6370)

## 0.53.0 Release (March 23, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.53.0).

* Import: paint dots also during the finalize phase (#6366)
* Use relocated protobuf runtime (#6355)
* Build/publishing: Proper Gradle module metadata for shadow-jars (#6361)
* Intellij: exclude `/.mvn` + `/ui/node_modules` (#6364)
* Support annotation-based configuration in "old server" tests (#6356)
* Mongo/storage: use `replaceOne` instead of `findAndReplaceOne` (#6363)
* Compatibility-test: allow adapter-configuration via system properties (#6353)
* Compatibility-tests: require Nessie >= 0.42.0, cleanup code (#6352)
* CI: Bump newer-java-version WF from 19 to 20 (#6348)
* Properly return `ParamConverterProvider` errors as HTTP/400 (#6346)
* Remove InstantParamConverterProvider (#6341)
* Update release notes template, remove "Docker Hub" (#6339)
* Build: Fix Jackson-annotations configuration for build-plugins int-test (#6337)
* Nit: Remove unnecessary null-check (#6344)

## 0.52.3 Release (March 20, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.52.3).

* Ninja: fix release WF
* Ninja: fix release WF
* Fix release WF (#6334)
* New storage: fix writetamp collision in C* integration tests (#6332)
* Use positive repo erase confirmation codes. (#6317)
* Forbid special ASCII chars in content keys (#6313)
* GH WF: Remove `concurrency` from PR jobs (#6314)
* Replace Docker Hub with ghcr.io (#6305)
* Fix param examples for getSeveralContents() (#6302)
* Fix labeler config (#6303)
* Workaround UI issues in ghcr.io + quay.io (#6299)
* Fix docker-sync WF typo (#6296)
* GH WF to sync container repositories (#6295)
* Content-tool: Add command to create missing namespaces (#6280)
* Ensure parent namespace(s) for commited content objects exist (#6246)
* Core reorg: use `api/` and `integrations/` directories (#6288)
* Add ACKNOWLEDGEMENTS.md (#6289)
* Allow some integration tests to be executed on macOS (#6266)
* Build/CI: Allow renovate to update the Maven wrapper (#6286)
* Update the Docker image section of main README.md (#6267)
* Ignore jEnv .java-version file (#6268)
* Use JvmTestSuite + JacocoReport (#6231)
* Content-generator tool changes for 'Ensure parent namespace(s) for commited content objects exist' PR (#6265)
* Test changes for 'Ensure parent namespace(s) for commited content objects exist' PR (#6263)
* CLI tool cheanges for 'Ensure parent namespace(s) for commited content objects exist' PR (#6264)
* compatiblity-tests: Extracted test changes for namespace-validation (#6261)
* Fix maven group id in docs (#6259)
* Add helper functions to `Namespace` and `ContentKey()` (#6256)
* Let ITs against Nessie/Quarkus send back stack traces (#6257)
* Replace `Key` with `ContentKey` (#6242)
* New DynamoDB storage - do not pull in the Apache HTTP client (#6243)
* Add missing Gradle build scan for last check (#6254)
* Fix `AbstractDatabaseAdapter.removeKeyCollisionsForNamespaces` (#6253)
* Fix wrong parameter validation override (#6241)
* Nit: remove mentions of dependabot (#6239)
* New Nessie storage model (#5641)

## 0.52.2 Release (March 20, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.52.2).

* Ninja: fix release WF
* Fix release WF (#6334)
* New storage: fix writetamp collision in C* integration tests (#6332)
* Use positive repo erase confirmation codes. (#6317)
* Forbid special ASCII chars in content keys (#6313)
* GH WF: Remove `concurrency` from PR jobs (#6314)
* Replace Docker Hub with ghcr.io (#6305)
* Fix param examples for getSeveralContents() (#6302)
* Fix labeler config (#6303)
* Workaround UI issues in ghcr.io + quay.io (#6299)
* Fix docker-sync WF typo (#6296)
* GH WF to sync container repositories (#6295)
* Content-tool: Add command to create missing namespaces (#6280)
* Ensure parent namespace(s) for commited content objects exist (#6246)
* Core reorg: use `api/` and `integrations/` directories (#6288)
* Add ACKNOWLEDGEMENTS.md (#6289)
* Allow some integration tests to be executed on macOS (#6266)
* Build/CI: Allow renovate to update the Maven wrapper (#6286)
* Update the Docker image section of main README.md (#6267)
* Ignore jEnv .java-version file (#6268)
* Use JvmTestSuite + JacocoReport (#6231)
* Content-generator tool changes for 'Ensure parent namespace(s) for commited content objects exist' PR (#6265)
* Test changes for 'Ensure parent namespace(s) for commited content objects exist' PR (#6263)
* CLI tool cheanges for 'Ensure parent namespace(s) for commited content objects exist' PR (#6264)
* compatiblity-tests: Extracted test changes for namespace-validation (#6261)
* Fix maven group id in docs (#6259)
* Add helper functions to `Namespace` and `ContentKey()` (#6256)
* Let ITs against Nessie/Quarkus send back stack traces (#6257)
* Replace `Key` with `ContentKey` (#6242)
* New DynamoDB storage - do not pull in the Apache HTTP client (#6243)
* Add missing Gradle build scan for last check (#6254)
* Fix `AbstractDatabaseAdapter.removeKeyCollisionsForNamespaces` (#6253)
* Fix wrong parameter validation override (#6241)
* Nit: remove mentions of dependabot (#6239)
* New Nessie storage model (#5641)

## 0.52.1 Release (March 20, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.52.1).

* Fix release WF (#6334)
* New storage: fix writetamp collision in C* integration tests (#6332)
* Use positive repo erase confirmation codes. (#6317)
* Forbid special ASCII chars in content keys (#6313)
* GH WF: Remove `concurrency` from PR jobs (#6314)
* Replace Docker Hub with ghcr.io (#6305)
* Fix param examples for getSeveralContents() (#6302)
* Fix labeler config (#6303)
* Workaround UI issues in ghcr.io + quay.io (#6299)
* Fix docker-sync WF typo (#6296)
* GH WF to sync container repositories (#6295)
* Content-tool: Add command to create missing namespaces (#6280)
* Ensure parent namespace(s) for commited content objects exist (#6246)
* Core reorg: use `api/` and `integrations/` directories (#6288)
* Add ACKNOWLEDGEMENTS.md (#6289)
* Allow some integration tests to be executed on macOS (#6266)
* Build/CI: Allow renovate to update the Maven wrapper (#6286)
* Update the Docker image section of main README.md (#6267)
* Ignore jEnv .java-version file (#6268)
* Use JvmTestSuite + JacocoReport (#6231)
* Content-generator tool changes for 'Ensure parent namespace(s) for commited content objects exist' PR (#6265)
* Test changes for 'Ensure parent namespace(s) for commited content objects exist' PR (#6263)
* CLI tool cheanges for 'Ensure parent namespace(s) for commited content objects exist' PR (#6264)
* compatiblity-tests: Extracted test changes for namespace-validation (#6261)
* Fix maven group id in docs (#6259)
* Add helper functions to `Namespace` and `ContentKey()` (#6256)
* Let ITs against Nessie/Quarkus send back stack traces (#6257)
* Replace `Key` with `ContentKey` (#6242)
* New DynamoDB storage - do not pull in the Apache HTTP client (#6243)
* Add missing Gradle build scan for last check (#6254)
* Fix `AbstractDatabaseAdapter.removeKeyCollisionsForNamespaces` (#6253)
* Fix wrong parameter validation override (#6241)
* Nit: remove mentions of dependabot (#6239)
* New Nessie storage model (#5641)

## 0.52.0 Release (March 20, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.52.0).

* New storage: fix writetamp collision in C* integration tests (#6332)
* Use positive repo erase confirmation codes. (#6317)
* Forbid special ASCII chars in content keys (#6313)
* GH WF: Remove `concurrency` from PR jobs (#6314)
* Replace Docker Hub with ghcr.io (#6305)
* Fix param examples for getSeveralContents() (#6302)
* Fix labeler config (#6303)
* Workaround UI issues in ghcr.io + quay.io (#6299)
* Fix docker-sync WF typo (#6296)
* GH WF to sync container repositories (#6295)
* Content-tool: Add command to create missing namespaces (#6280)
* Ensure parent namespace(s) for commited content objects exist (#6246)
* Core reorg: use `api/` and `integrations/` directories (#6288)
* Add ACKNOWLEDGEMENTS.md (#6289)
* Allow some integration tests to be executed on macOS (#6266)
* Build/CI: Allow renovate to update the Maven wrapper (#6286)
* Update the Docker image section of main README.md (#6267)
* Ignore jEnv .java-version file (#6268)
* Use JvmTestSuite + JacocoReport (#6231)
* Content-generator tool changes for 'Ensure parent namespace(s) for commited content objects exist' PR (#6265)
* Test changes for 'Ensure parent namespace(s) for commited content objects exist' PR (#6263)
* CLI tool cheanges for 'Ensure parent namespace(s) for commited content objects exist' PR (#6264)
* compatiblity-tests: Extracted test changes for namespace-validation (#6261)
* Fix maven group id in docs (#6259)
* Add helper functions to `Namespace` and `ContentKey()` (#6256)
* Let ITs against Nessie/Quarkus send back stack traces (#6257)
* Replace `Key` with `ContentKey` (#6242)
* New DynamoDB storage - do not pull in the Apache HTTP client (#6243)
* Add missing Gradle build scan for last check (#6254)
* Fix `AbstractDatabaseAdapter.removeKeyCollisionsForNamespaces` (#6253)
* Fix wrong parameter validation override (#6241)
* Nit: remove mentions of dependabot (#6239)
* New Nessie storage model (#5641)

## 0.51.1 Release (March 07, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.51.1).

* DOCS PR for Maven group-ID refactoring (#6207)
* Properly sign pom-relocations (#6236)
* Allow tests to customize Nessie REST API URI resolution. (#6234)
* build/nit: Remove superfluous settings (#6232)
* Add SwaggerHub badge to README (#6233)
* Make V2 ref path-param parsing independent of `Reference` (#6224)
* Add tests to validate that API v1 responses do not have v2 attributes. (#6222)
* Fix codecov-n-builds (#6230)
* V2 REST declares wrong response types in OpenAPI spec (#6211)
* Fix native images after recent rocksdb version bump (#6216)
* Symlink gradle-wrapper.jar, unignore gradle-wrapper.jar (#6219)
* upgrade GH WFs to ubuntu-22.04 (#6206)
* Maven group-ID refactoring (#6197)
* Fix: `Content.Type` is a string (#6202)
* Git ignore `__pycache__` (#6201)
* Compatibility tests code cleanup (#6198)
* Site: Update community page with updated chat information (#6192)
* Move Nessie speecific build code into this build (#6196)
* Nit: javadoc (#6193)
* Gradle 8 adoption for NesQuEIT (#6166)
* mac-CI: Workaround for brew-upgrade issue (#6184)

## 0.51.0 Release (March 06, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.51.0).

* Allow tests to customize Nessie REST API URI resolution. (#6234)
* build/nit: Remove superfluous settings (#6232)
* Add SwaggerHub badge to README (#6233)
* Make V2 ref path-param parsing independent of `Reference` (#6224)
* Add tests to validate that API v1 responses do not have v2 attributes. (#6222)
* Fix codecov-n-builds (#6230)
* V2 REST declares wrong response types in OpenAPI spec (#6211)
* Fix native images after recent rocksdb version bump (#6216)
* Symlink gradle-wrapper.jar, unignore gradle-wrapper.jar (#6219)
* upgrade GH WFs to ubuntu-22.04 (#6206)
* Maven group-ID refactoring (#6197)
* Fix: `Content.Type` is a string (#6202)
* Git ignore `__pycache__` (#6201)
* Compatibility tests code cleanup (#6198)
* Site: Update community page with updated chat information (#6192)
* Move Nessie speecific build code into this build (#6196)
* Nit: javadoc (#6193)
* Gradle 8 adoption for NesQuEIT (#6166)
* mac-CI: Workaround for brew-upgrade issue (#6184)

## 0.50.0 Release (February 24, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.50.0).

* Strip jakarta.* annotations for Java 8 (#6172)
* Fix Nessie GH URL in docs (#6167)
* Refactor advanced configuration (#6159)
* Site: Update community links CI and README.md CI (#6165)
* Redesign telemetry support in Helm chart (#6153)
* Disable versioned-transfer ITs in macOS CI (#6164)
* Remove rocksdb.dbPath from Helm chart values (#6149)
* Fix console log level setting in Helm charts (#6158)
* Minor fixes to the build-push-images.sh script (#6155)
* mac-os/win CI - add retry for steps that regularly timeout/hang (#6152)
* Add missing jakarta.* annotations (#6150)
* Disable intTest using containers/podman for macOS CI (#6148)
* Improve nessie-ui build a little bit (#6147)
* Bump npm from 7.24.2 to 9.4.2 and nodejs from 16.14.2 to 18.4.1 (#6145)
* Rename jobs for mac+win GH workflows (#6143)
* Allow all GH WFs to become "required checks" (#6131)
* Label `model` changes with `pr-integrations` (#6132)
* GH workflow to smoke test Docker images (#6127)
* Fix typo in release notes (#6129)
* PR Auto labeler (#6128)
* Fix renovate configuration (#6124)

## 0.49.0 Release (February 17, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.49.0).

* Update GH WFs to publish Java images for many platforms (#6110)
* GH uploaded artifacts retention (#6115)
* Test: Update the validation to unblock query-engine-integration-tests (#6117)
* GH workflows: prevent running a bunch of stuff on forks (#6116)
* Revert changes to Chart.yaml maintainers section (#6113)
* Fail test discovery in MultiEnvTestEngine if no environments are defined (#6108)
* Disable :nessie-compatibility-common tests on macOS (#6106)
* Disable NPM tests on Windows (#6107)
* Simplify Quarkus build configuration (#6103)
* Automatically generate Helm chart documentation (#6095)
* Add Jakarta's ws-rs to `javax.ws.rs.*` (#6067)
* Add Jakarta's validation constraints to `javax.validation.constraints.*` (#6096)
* Add Jakarta's `@Inject` to `javax.Inject` (#6093)
* Add Jakarta's `@RequestScoped` to `javax.enterprise.context.RequestScoped` (#6092)
* Add Jakarta's `@Nullable` to `javax.annotation.Nullable` (#6088)
* Add Jakarta's `@Nonnull` to `javax.annotation.Nonnull` (#6066)
* Ability to configure PVC selector labels (#6074)
* Gatling simulation for a mixed workload (#6068)
* Split ws-rs-api implementors into javax and jakarta (#6065)
* Add API v2 test for commit parents (#6073)
* Nit: replace Jetbrains not-null annotation import (#6072)
* CI: Fix PR integrations workflow (#6071)
* Make Nessie build with `javax.*` and `jakarta.*` dependencies (#6064)
* Remove `@ThreadSafe` annotation, no `jakarta.*` counterpart (#6063)
* Remove `@CheckForNull` annotation, no `jakarta.*` counterpart (#6062)
* Tests: let tests in nessie-compatibility-common use soft-assertions (#6061)

## 0.48.2 Release (February 08, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.48.2).

* Fix dependencies in :nessie-compatibility-common (#6055)
* fix website link in README (#6056)
* Nit: rename build utility function to `buildForJava11` (#6053)
* Reenable JVM monitoring in native images (#6052)
* Cleanup InmemoryStore after tests (#6048)
* Cleanup README.md badges (#6051)
* CI: Prevent OOM/GCLocker-issue in tests / disable Micrometer JVM-Metrics for tests (#6050)
* Clear in-memory database-adapter between REST-tests in Quarkus (#6049)

## 0.48.1 Release (February 06, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.48.1).

* Release-WF: Fix openapi yaml attachment (#6040)

## 0.48.0 Release (February 06, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.48.0).

* Produce NessieBadRequestException when paging is not available (#6020)
* Quarkus builds: modernize & simplify (#6019)
* Expose definitive Nessie API version to tests (#6021)
* CI: Gradle cache read-only for snapshot publication workflow (#6018)
* REST v2: return parent commit IDs in `CommitMeta.getParentCommitHashes` (#6001)
* REST API v2: get-multiple-namespaces w/ option for direct children (#6010)
* Quarkus-nit: build script cleanup (#6016)
* Refactor API v2 reference resolution logic into a utility class (#6012)
* Quarkus: make `QuarkusBuild` not caching (#6015)
* Add missing explicit rename-table test (#6008)
* Do not memoize CommitMeta.properties, it is derived (#6006)
* CI: Do not cache Python stuff (#6004)
* Add support for API v2 to the compatibility test framework (#6000)
* Add java client-based test for Entry.getContentId() (#5996)
* UI: Sort properties in commit details view (#5987)
* Allow retrieval of `Content` with `get-entries` for API v2 (#5985)
* CI: fix helm-testing action refs (#6005)
* CI: Helm testing (#6003)
* Revert "Make `QuarkusBuild` cacheable" (#5997)
* Nessie client doc improvements (#5992)
* Add effective-reference to get-entries, diff and modify-namespace responses (#5984)
* Build: Fix task dependency warnings for code coverage (#5990)
* Let content-generator use REST API v2 (#5948)
* REST API v2 : Let 'get-content(s)' return the reference (#5947)
* Minor convenience methods `getElementsArray()` to `ContentKey` + `Namespace` (#5983)
* Nit: Prevent java compilation warning (#5978)
* Re-make Gatling sims debuggable after the move to Gradle (#5972)
* Gatling Simulations - error out Nessie actions by default (#5971)
* Helm: Add storage class for RockDB PVC (#5981)
* Fix `MathAbsoluteNegative` in `NonTransactionalDatabaseAdapter` (#5979)
* Make `QuarkusBuild` cacheable (#5974)
* Add `@JsonView` really everywhere (#5965)
* Add PVC for RocksDB in Nessie Helm chart (#5952)
* Let Gatling simulations use Nessie API v2 (#5970)
* Build: Make `QuarkusGenerateCode` tasks cacheable (#5973)
* Nit: `var` -> `val` in quarkus builds (#5975)
* Remove usage of deprecated `StreamingUtil` (#5954)
* Nit: typo (#5951)
* Add `@JsonView` everywhere (#5953)
* REST v2: Correct namespace key evaluation for delete + get-multiple namespaces (#5946)
* Minor: immutables contstructors for `Branch` + `Tag` + `Detached` (#5944)
* Replace use of unpaged `get()` when listing references from SQL extensions (#5950)
* Add utility method `CommitResponse.toAddedContentsMap()` (#5945)
* Minor Namespace.isEmpty() optimization (#5943)
* Use the Smallrye OpenAPI Gradle plugin (#5936)
* Add `.scalafmt.conf` for IntelliJ (#5942)
* Update merge "no hashes" error message (#5938)
* Separate version catalog for `buildSrc/` (#5935)
* Re-add batched access checks & replace `j.s.AccessControlException` (#5930)
* Move Jersey test dependencies to where they belong to (#5933)
* Refactor Service/REST/API testing (#5924)
* Unify `*ApiImplWithAuthorization` and "base" `*ApiImpl` classes (#5927)
* REST v2 - fetch HEAD if hashOnRef is not specified (#5921)
* Prepare protocol independent pagination support for diff, get entries, commit log, get all refs (#5901)
* Just rename test classes (#5920)
* DatabaseAdapterExtension: configure database-adapter via a nested test class' parent context (#5919)
* Bump Spark to 3.1.3/3.2.3/3.3.1 (#5909)
* CI/Win: exclude UI tests (#5908)

## 0.47.1 Release (January 20, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.47.1).

* Fix Jacoco reporting in Nessie Quarkus projects (#5902)
* Return latest state of deleted reference (#5905)
* Disable JVM monitoring in native images (#5903)

## 0.47.0 Release (January 18, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.47.0).

* Use custom CDI extension for injecting Principals (#5883)
* Update V2 commit-response to include generated content-IDs (#5880)
* Add content-ID to REST API v2 entries endpoint (#5879)
* Gatling: properly prefer `sim.duration.seconds` over `sim.commits` (#5847)
* Spark extensions tests - no CID for new content & common-ancestor (#5877)
* Content generator: no CID for new content, properly use expectedContent+CID for existing (#5876)
* Deltalake: no CID for new content, properly use expectedContent+CID for existing (#5875)
* Quarkus & JAX-RS tests - no CID for new content & common-ancestor (#5874)
* Do not expose junit-jupiter-engine via test artifacts (#5870)
* Gatling: allow running the Gatling simulations against an external Nessie (#5848)
* Testing: unify container start retry mechanism (#5844)
* Testing/benchmarking: fix configuration for persist-bench via system properties (#5843)
* Add macOS + Windows build check badges to README.md (#5849)
* Use REST API v2 in Gatling simulations (#5846)
* Testing: use full Quarkus listen-URL instead of just the port (#5845)
* GH WF: simplify some steps (#5869)

## 0.46.7 Release (January 12, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.46.7).

* Expose Nessie API version to custom client builders in tests (#5839)

## 0.46.5 Release (January 12, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.46.5).

* CI: Fix jobs.if condition for mac+win workflows (#5840)
* nessie-quarkus-cli/jacoco: use a fresh jacoco data file (#5833)
* CI: cross-check macOS + Windows (#5705)
* Validate runtime parameters in service implementations. (#5828)
* Testcontainers: retry container launch (#5831)
* Test/CI: Introduce test parallelism constraints for test tasks (#5824)
* Actually assign `main` in AbstractContentGeneratorTest (#5830)
* Exclude compatibility tests from code-coverage (#5826)
* Disable Deltalog integration tests on Windows (#5825)
* Disable Deltalog integration tests on macOS (#5822)
* Disable tests on projects using testcontainers on Windows (#5817)
* Disable quarkus-jacoco on `:nessie-quarkus-cli` (#5820)
* Fix newline when testing under Windows (#5814)
* model/test: Platform independent line separator (#5815)
* Testing: add logging for projects using testcontainers (#5813)
* Testing: allow `test.log.level` in compatibility tests (#5812)
* Nit: compatibility-tests: remove wrong comment (#5816)
* Disable tests on Windows that have strict time requirements (#5807)
* Disable compatibility tests on Mac (#5805)
* GC/Tests: Ignore schema-less base-uri-test on Windows (#5804)
* Prevent using the Minio Extension on non-Linux OS (#5803)
* Proper Path handling in GC tool CLI (#5799)
* Make LocalMongoResource a bit more resilient (#5802)
* Testcontainers: increase number of start attempts from 1 to 5 (#5801)
* Disable Quarkus dev services for tests (#5800)
* Proper Path handling in IcebergContentToFiles (#5798)
* Proper Path handling in TestNessieIcebergViews (#5797)
* Fix ZIP importer handle leak (#5796)
* CI: Fix results-upload for PRs (#5795)
* CI: Remove deprecated `::set-output` (#5794)
* Nit: Suppress ClassCanBeStatic warnings (#5793)

## 0.46.3 Release (January 06, 2023)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.46.3).

* Remove grep -v from the HISTORY.rst generation script (#5778)
* Fix MultiEnvTestFilter to pass inner test classes (#5775)
* Make OlderNessieServersExtension inject Nessie URIs (#5770)
* Remove duplicate startHash call in `HttpGetCommitLog` (#5756)

## 0.46.2 Release (December 28, 2022)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.46.2).

* Add validation annotations to backend service interfaces (#5740)
* Docs: Update README.md for 0.46.0 release (#5734)

## 0.46.0 Release (December 20, 2022)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.46.0).

* Make generated OpenAPI type names more readable (#5732)
* Add serialized form tests for `CommitMeta` v2 (#5731)
* Add v2 attributes to `CommitMeta` (#5706)
* CI: No duplicate checkstyle task runs (#5703)
* Allow publishing Gradle build scans from CI (#5701)
* Remove legacy Spark extensions (#5704)
* Fix flaky TestKeyGenerator (#5702)
* Support merge/transplant message overrides (#5686)
* Add common ancestor commit in merge tests (#5696)
* Add global test timeout for versioned/persist/ (#5690)
* Add DeleteContent command (#4773)
* Expose updated reference data in java client APIs (#5670)
* Fix OpenAPI docs for the required "expected hash" parameters (#5665)
* Expose MergeBehavior to java clients (#5682)
* Expose `CommitResponse` in java client API (#5673)
* Migrate `FetchOption` to the `model` package (#5667)
* Add README.md to model (#5668)
* Use API v2 in GC CLI (#5660)
* Add pagination params to the Diff API v2 (#5452)
* Replace deprecated native build parameters (#5662)
* Move Graal/native registration to nessie-quarkus-common (#5661)
*   Increase gradle memory in integration tests (#5654)
* Remove unused Jaeger properties (#5647)
* Validate ID for explicitly created Namespaces (#5644)
* Gradle: no longer run test classes concurrently (#5653)
* Nit: remove TODO (#5652)
* Fix GetReferenceParams description for API v2 (#5639)
* Import: move repo-setup part to nessie-versioned-transfer (#5640)
* Export using reference/commit-log scanning (#5635)
* Switch nessie-client to use OpenTelemetry (#5607)
* Version-store tests: use proper Put operations (#5614)
* DatabaseAdapter/identify-heads-and-forks: Allow commit-log scanning (#5638)
* Version-store tests: Adjust test to currently undetected transplant conflict (#5613)
* DatabaseAdapterExtension: Allow adapter-configurations on fields (#5637)
* Export: store exporting Nessie version and show during import (#5636)
* Adopt REST tests to stricter validations (#5612)
* PersistVersionStore: expose missing additional-parents (#5633)
* Export/import: explicit tests for exporter/importer implementations (#5631)
* DatabaseAdapter/identifyHeadsAndForks: Don't return `NO_ANCESTOR` as fork point (#5632)
* Export/import: remove unused functionality from BatchWriter (#5630)
* Rely on new `NessieVersion` in CLI tools (#5629)
* Allow `-` as a reference name in v2 REST paths (#5618)
* Export/import: move file related code to separate package (#5628)
* Use soft-assertions in jaxrs-tests (#5610)
* Adopt versioned-tests to not set cid for "new" `Content`; minor refactoring for merge/transplant (#5611)
* Deprecate commit lists in merge-results (#5599)
* Add Nessie version into nessie-model.jar (#5609)
* Minor verstion-store commit-log test refactoring (#5604)
* Minor merge test enhancements (#5603)
* Add "diff test" with a key "in between" (#5602)
* Add a some test cases for "reference not found" scenarios (#5601)
* Factory methods for EntriesResponse.Entry + DiffResponse.Entry + MergeResult.KeyDetails (#5600)
* Add expected content to v2 REST update namespace (#5598)
* Switch to OpenTelemetry in Nessie Quarkus server (#5605)
* Minor code clean-up in client-side Namespace operations (#5594)
* Fix v2 URL path mapping for getReferenceByName (#5596)
* Add REST API v2 (#5004)
* Fix `@Parameter` annotations in `DiffParams` (#5589)
* Build UI against Nessie API v1 from 0.45.0 (#5587)

## 0.45.0 Release (November 29, 2022)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.45.0).

* Fix OpenAPI spec for DiffApi (#5584)
* Content generator: allow functional key patterns and more (#5575)
* CI: auto-destruct spawned Nessie Quarkus runner JVM (#5583)
* Scala/Gradle: keep Scala compiler alive, bump Scala 2.13 to 2.13.10 (#5576)
* Fix BaseExceptionMapper's NPE when an Exception's message is null (#5563)
* Add RefreshContent command (#5551)
* Fix erasing repo descriptions (#5557)
* Perftest: allow passing system properties to launched Nessie (#5556)
* Transfer: completely abstract `DatbaseAdapter` from core export/import (#5555)
* Remove dependency to database-adapter type from `ImportResult` (#5553)
* Deprecate `VersionStore.getRefLog()` (#5554)
* Nessie export/import: update abstraction for file-typed stuff (#5545)
* Nessie CLI: rename `call()` to `callWithDatabaseAdapter()` (#5540)
* Nessie import: check export version (#5543)
* Nessie CLI: Update wording for in-memory warning (#5542)
* Nessie/Quarkus: use `Instance<DatabaseAdapter>` (#5541)
* Update verification code for Nessie CLI erase-erpository (#5539)
* Add content-info Quarkus CLI command (#5501)
* Reduce memory pressure during Quarkus CLI integration tests. (#5530)
* Bump+change Quarkus builder image to Mandrel + 22.3 (#5526)
* renovate: put python back on weekly schedule (#5519)
* Docs: Update Spark Python/Java API docs (#5517)
* Fix latest Nessie and Iceberg versions in side docs (#5499)
* Refactor Nessie-Jax-RS extension a little bit (#5486)
* renovate: fix fileMatch regexp (#5489)
* fix renovate python requirements file pattern (#5481)
* Proper put-update-operation for namespace-update (#5484)
* Move tests from o.p.jaxrs to o.p.jaxrs.tests (#5483)
* Fix a few checkstyle warnings (#5482)
* Update protobuf plugin to 0.9.1 (#5457)
* Documentation accommodating new version format of Iceberg and Nessie artifacts (#5371)
* Pull `ProtobufHelperPlugin` from `gradle-build-plugins` (#5456)
* Refactor some test code to soft-assertions (#5446)
* Move Merge/Transplant classes to the api.v1.params package (#5435)
* Move old API classes into the api.v1 package (#5419)
* Move some tests from db-adapter test code to verstion-store test code (#5445)
* Support method-level NessieApiVersions annotations (#5436)
* Introduce multi-version API test framework (#5420)
* Extract service-side interfaces for RefLog and Namespace services (#5418)
* Extract common java client builder code (#5411)
* Extract service-side interfaces for client-facing services (#5412)
* Remove OpenAPI spec properties from build script. (#5413)
* Build UI against Nessie API v1 from 0.44.0 (#5408)
* Refactor TestAuthorizationRules (#5399)
* Java 11 HttpClient (#5280)
* Testing/nit: Logging for :nessie-s3minio ITs (#5398)
* Add non-trivial tests for assign branch/tag operations (#5395)
* Fix version in the Nessie Helm Chart (#5392)
* Extract a multi-env test engine into a module. (#5339)
* Move Nessie Client construction into a JUnit5 extension (#5370)
* Record next development version (#5387)
* GH workflows: Add missing `cache-read-only: true` (#5385)
* GH create release WF: next version not properly recorded (#5386)

## 0.44.0 Release (October 18, 2022)

See [Release information on GitHub](https://github.com/projectnessie/nessie/releases/tag/nessie-0.44.0).

* Ninja: fix create-release WF
* Ninja: fix GH env reference
* Update README mentioning Iceberg 1.0 (#5384)
* Add Dan + Vladimir to devs list (#5381)
* GH release WF: default to "minor" version bump (#5380)
* GH release WF: Fix wrong task name (#5379)
* Automatically update release text files (#5377)
* GH release WF - fix log exclusion + log filter for rel-notes (#5378)
* Nessie GC: Docs (#5209)
* Nessie GC: Command line tool (#5227)
* Nessie GC: Iceberg functionality (#5207)
* Avoid direct dependency on iceberg-bundled-guava (#5366)
* quarkus-server tests use dynamic port from env (#5352)
* Fix missing placeholder for Preconditons.checkState (#5360)
* fix GH workflows still mentioning maven (#5353)
* Remove httpClient param from AbstractRest.init (#5354)
* Update pretty-ms to 8.x (#5341)
* DynamoDB related test changes (#5338)
* Split unsquashed merge tests into dedicated test methods (#5328)
* Update testing-library-react to 13.x (#5334)
* Bump actions/checkout from v3 to v3.1.0 (#5335)
* Update material-ui to mui 5.x.x (#5326)
* nessie-client/test: compress responses for all relevant HTTP methods (#5323)
* Fix micrometer path replacement patterns (#5321)
* Renovate: labels for java/javascript/python (#5318)
* Revert protobuf to 3.21.6 (#5317)
* Renovate: limit to 2 PRs per hour (#5319)
* Isolate http-level test from java client-level tests (#5314)
* Skip newer-java workflow on forks (#5294)
* Nessie client tests: Replace JDK's HTTP server w/ Jetty (#5285)
* Move internal classes of the Nessie HTTP client (#5286)
* GH/WF: Use `temurin` instead of 'zulu` (#5289)
* Nessie-client tests: do not compile w/ older Jackson versions (#5287)
* Test Nessie client with Java 8 (#5284)
* Integrate Jackson-version tests into Gradle build (#5279)
* Migrate to Gradle version catalogs (#5167)
* Unify Postgres container version declaration for tests
* Migrate from dependabot to renovate (#5166)
* Testing pre-requisites for Nessie GC: Two S3 testing projects (#5142)
* Fix iceberg verison on web site (#5222)
* Java 19 testing (#5221)
* Automatic patch releases (#5214)
* Unsupport 0.30.x versions (#5212)
* WF: Remove Maven part (#5213)
* Nessie GC: JDBC Live-Set-Repository (#5208)
* Allow Hadoop Spark config in tests (#5206)
* Nessie GC: mark & sweep (`gc-base` module only) (#5144)
* Slight build scripts change for Java11+ target compat (#5203)
* Fix test failure caused by #5147 (#5204)
* No longer write ref-log entries for commit/merge/transplant (#5147)
* Schedule dependabot for npm + pip less frequently (#5201)

## 0.43.0 Release (September 15, 2022)

**Rolling upgrades from versions before 0.40.0 are not supported!**

* Prepare for Nessie GC
* Nessie export/import functionality
* Use Graal 22.2-java17 for native images
* Several test and build improvements

## 0.42.0 Release (August 26, 2022)

**Rolling upgrades from versions before 0.40.0 are not supported!**

* Key list creation fixes
* Pluggable content types

## 0.41.0 Release (August 5, 2022)

**Rolling upgrades from versions before 0.40.0 are not supported!**

* Do not persist and expose attached content metadata
* Fix issue when looking up key in an open-addressing-key-list

## 0.40.3 Release (August 1, 2022)

**Rolling upgrades from versions before 0.40.0 are not supported!**

* Remove Quarkus-BOM dependency from non-Quarkus projects

## 0.40.2 Release (August 1, 2022)

**Rolling upgrades from versions before 0.40.0 are not supported!**

* Dependency issues fixed

## 0.40.1 Release (July 25, 2022)

**Rolling upgrades from versions before 0.40.0 are not supported!**

* Fix key-lists issue resulting in server-errors (`ArrayIndexOutOfBoundsException`)

## 0.40.0 Release (July 22, 2022)

**Rolling upgrades from earlier versions are not supported!**

* Support Spark 3.1 (Scala 2.12), Spark 3.2 (Scala 2.12 + 2.13), Spark 3.3 (Scala 2.12 + 2.13)
* Support Iceberg 0.14.0
* Nessie Spark SQL extensions: handle timestamps w/ time-zones
* Nessie Spark SQL extensions: fix handling of `USE`d references for `CREATE/ASSIGN BRANCH/TAG`
* Detailed merge/transplant result to allow inspection of conflicts
* Merge/transplant optionally allow "force-keep" & "force-merge" of conflicting content-keys
* Iceberg table metadata stored in Nessie
* Improvements to REST error handling
* Performance improvements when there are many content-keys
* Hard limit on content-key length (max 20 elements, total 500 characters)
* Prevent (accidental) deletion of default branch
* Improved usage of automatic paging via `NessieApi`
* Improvements to Nessie server health checks
* Add rolling-upgrade test suite in regular CI
* Daily testing against Java 17 + newer
* Switched to Java 17 in native images
* Build switched from Maven to Gradle

## 0.30.0 Release (May 13, 2022)

* Add commit-ID to KeyListEntry when writing new key-lists
* Do not process old key-lists when retrieving values
* Helm: Fix k8s version detection in ingress template
* Database-adapter: commit optimizations
* Remove the configurable default for the configurable values for getDefaultMaxKeyListSize
* Dynamo/Mongo/TX: use bulk/batch requests where possible

## 0.29.0 Release (May 5, 2022)

* Spark SQL: Configure ref.hash for NessieCatalog only when explicitly requested
* Escape all column names in SQL DML+DDL statements
* Use hashOnRef when fetching Namespaces
* Helm: Add ingress support for Kubernetes >=1.22
* Fix CockroachDB transaction-retry behavior

## 0.28.0 Release (April 26, 2022)

* Generate unique content IDs for explicitly created namespaces
* Fix patterns for metrics
* Various test improvements (CI + build)
* Various minor code fixes (fixes for errorprone warnings)

## 0.27.0 Release (April 14, 2022)

* Support for Namespace properties
* Make NessieContentGenerator extensible

## 0.26.0 Release (April 12, 2022)

**Rolling** upgrades from an older Nessie release to 0.26.0 or newer are not supported.

* Remove global state for Iceberg tables + views
* Internal optimizations in database adapters, version store and API endpoints
* Change 'marker' character to indicate `.` in namespace/table identifiers from ASCII 0 to `\u001D`
* Opt-in to force-merge or not merge specific content keys (also for transplant)
* Squash merged and transplanted commits by default (with opt-out)

## 0.25.0 Release (April 6, 2022)

* Nessie Quarkus Server can use Postgres as its backend database
* Explicitly define behavior of multiple commit-operations in a commit
* Load correct view metadata for a given ref

## 0.24.0 Release (March 31, 2022)

* Prevent explicit creation of empty namespaces
* Add content-id to `BatchAccessChecker.canReadContentKey()`

## 0.23.1 Release (March 23, 2022)

* Support Namespaces
* CI "perf tests" improvements
* SQL Extension: Fix Create reference from a hash on non-default reference
* Enhance authorization checks
* Support custom annotations on Nessie Helm service

## 0.23.0 Release (March 23, 2022)

(not properly released)

## 0.22.0 Release (March 11, 2022)

* Improve performance of `getValues`
* Global-log compaction
* Store-level maintenance CLI
* Reduce number of tags for micrometer
* Grafana Dashboard for Nessie service
* Add new commands to generate-content tool

## 0.21.2 Release (March 2, 2022)

* Fix serialization issue

## 0.21.1 Release (March 2, 2022)

* (no user visible changes)

## 0.21.0 Release (March 1, 2022)

* Add tracing to database-adapter internals
* Introduce compatibility and upgrade tests
* Refactor StreamingUtil class
* Support for Spark 3.1 + 3.2.1 in Nessie SQL extensions
* Proper usage of commit-id in Spark SQL extensions
* Add DELETE_DEFAULT_BRANCH access check

## 0.20.1 Release (February 17, 2022)

* (no user visible changes)

## 0.20.0 Release (February 16, 2022)

* Enable metrics for MongoDB by default
* Make try-loop-state configurable and add metrics
* Reorganize routes in UI
* Improve error reporting in Nessie Java client
* Various test improvements

## 0.19.0 Release (February 7, 2022)

* Reads using "detached" commit-ids w/o specifying a branch or tag name
* Bump Nessie client version in Nessie Spark-Extensions
* Support for Iceberg views (experimental)
* Diff endpoint supports named-references + commit-on-reference as well
* Add filtering for ref-log
* Rework and simplification of the Nessie UI code

## 0.18.0 Release (January 13, 2022)

* Add reflog support
* Uses commit-timestamp "now" for merged/transplanted commits
* Add new reflog command to the CLI
* Add support for Python 3.10
* Drop support for Python 3.6

## 0.17.0 Release (December 08, 2021)

* Rename 'query_expression' query param to 'filter'
* Rename 'max' query param to 'maxRecords'
* Rename 'fetchAdditionalInfo' query param to 'fetch' for better extensibility

## 0.16.0 Release (December 03, 2021)

* Mark optional fields as @Nullable / add validation for required fields in param classes
* Add CEL-filter to get-all-references
* Fix NPE for unchanged operation for fetching commit log with additional metadata
* Allow CEL-filtering on optional operations in get-commit-log

## 0.15.1 Release (December 01, 2021)

* Fix wrongly placed validation annotation

## 0.15.0 Release (December 01, 2021)

* Enhance commit log to optionally return original commit operations
* Optionally return commits ahead/behind, HEAD commit-meta, commit count,
  common ancestor for named references
* Add missing REST endpoint to retrieve diff between two references
* Web UI improvements

## 0.14.0 Release (November 12, 2021)

* Updated `IcebergTable` to track more information
* UI dependencies cleanup
* OpenAPI/REST API cleanup (breaking change)

## 0.12.1 Release (November 3, 2021)

* Test code improvements
* Swagger examples fixes
* Web UI improvements
* Faster local builds w/ `./mvnw -Dquickly`

## 0.12.0 Release (October 25, 2021)

* Specialize and document Nessie exceptions
* Adopt Helm chart with new Nessie server settings
* Bump to GraalVM 21.3

## 0.11.0 Release (October 20, 2021)

* Various doc + site improvements
* Fix Nessie's representation of global and on-reference state (Iceberg tables)
* Fix CLI log -n option
* Spark SQL extension improvements

## 0.10.1 Release (October 8, 2021)

* Spark SQL extension changes
* Various (Open)API and client (Java, Python) implementation changes to prepare for better
  backwards compatibility.
* JUnit extension based test support against different database/store types and configurations
* Unified version-store implementations into a part w/ the Nessie logic and a tier implementing
  database access (MongoDB, DynamoDB, RocksDB, PostgreSQL).
* Remove JGit

## 0.9.2 Release (August 26, 2021)

* Cleanup & fixes to OpenAPI examples, for Swagger UI
* Update Deltalake client to use version 1.0.0
* Drop Deltalake support for Spark 2
* Remove Hive-Metastore bridge
* Preparations for backwards-compatible Client-API
* Spark SQL Extensions: Introduce `IF NOT EXISTS` for `CREATE BRANCH`/`CREATE TAG`
* Spark SQL Extensions: Updates to work with Iceberg 0.12.0

## 0.9.0 Release (August 9, 2021)

* Support for the upcoming Iceberg `0.12.0` release for both Spark 3.0 + 3.1
* Add docs for Nessie's metadata authorization
* Add SPI for Nessie authorization with Reference implementation
* Create Helm chart for Nessie

## 0.8.3 Release (July 19, 2021)

* Fix issue in spark sql extensions
* Python CLI: Fix ser/de of DeltaLakeTable when listing contents

## 0.8.2 Release (July 15, 2021)

* Add JAX-RS server implementation based on Glassfish/Jersey/Weld for integration testing
  in Iceberg
* REST-API change: only accept named-references
* REST-API change: support time-travel on named-references
* REST-API change: Server-side commit range filtering
* OpenAPI: more explicit constraints on parameters
* OpenAPI: include OpenAPI yaml+json files in nessie-model artifact
* Remove already deprecated methods from ContentsApi
* Commit-log filtering on all fields of CommitMeta
* Use "Common Expression Language" for commit-log and entries filtering
* Spark-extensions for Iceberg
* Prepare for multi-tenancy
* Gatling support + simulations
* Python CLI: Fix ser/de of DeltaLakeTable when listing contents

## 0.7.0 Release (June 15, 2021)

* Server-side filtering improvements for entries-listing and log-listing
* Distinguish between author & committer in the Python CLI
* Allow setting author when committing via Python CLI
* Loosen pins for client install on Python cli
* Fix edge case when merging using in memory + jgit stores
* Gradle plugin improvements
* (Development) change to Google Code Style, add spotless plugin
* (CI) Add OWASP Dependency Check

## 0.6.1 Release (May 25, 2021)

* Gradle plugin improvements

## 0.6.0 Release (May 12, 2021)

* TreeApi.createReference() + commitMultipleOperations() return commit information
* Iceberg GC actions and a process to execute GC algorithm

## 0.5.1 Release (April 9, 2021)

* Fix Gradle plugin (non-deterministic order of dependencies causing failures)
* Fix Web-UI

## 0.5.0 Release (April 8, 2021)

* Iceberg table GC support
* Consistency fixes under high load
* Breaking changes to the backend to support richer commit metadata and data types
* Performance, metrics and tracing improvements
* Gradle plugin improvement for incremental builds

## 0.4.0 Release (March 8, 2020)

* rename base package to org.projectnessie
* NessieClient is now an interface and some easier builders
* initial implementation of GC algorithm
* major refactor of tiered classes for better modularity and extensibility
* observability improvements including better DynamoDB metrics and opentracing support for the client

## 0.3.0 Release (December 30, 2020)

* 118 commits since 0.2.1
* Replace jax-rs client with one based on HttpURLConnection
* Update Quarkus to 1.10.5
* Improvements to Server including better UI routing, validation checks on inputs etc
* Various improvements to python client and cli. Including python3.9 support

## 0.2.1 Release (October 30, 2020)

* Fix missing dateutil requirement for pynessie install
* Address path discovery in Gradle plugin (for testing in external integrations)

## 0.2.0 Release (October 29, 2020)

* Update [Nessie CLI](../tools/cli.md) commands to better match `git` syntax
* Update [REST Apis](../develop/rest.md) to be more consistent and better
* Add support for merge & cherry-pick in DynamoDB storage backend
* Add [WebUI](../tools/ui.md)
* Introduce new DynamoDB optimizations to support faster log and entry retrieval
* Update to Quarkus 1.9.1
* Expose the new [Store interface](https://github.com/projectnessie/nessie/blob/main/versioned/dynamodb/src/main/java/org/projectnessie/versioned/store/Store.java) for low level storage implementations
* Introduce Quarkus Gradle runner plugin for easier third-party testing (e.g. Iceberg)
* Enable [swagger-ui](../tools/ui.md) by default in Nessie service

## 0.1.0 Release (October 1, 2020)

* Initial release
