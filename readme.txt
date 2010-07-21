
Goals:
a) an archetype to generate the same project of seam-tools with jboss maven support.
b) a seam maven project that conforms to maven's directory structure, not eclipse's.

Issues:
*) let maven generate the application.xml file or not.
*) on import, an m2eclipse bug: uses *.ejb not *.jar for ejb module.  Need to manually edit .settings/org.eclipse.wst.common.component BEFORE deploy.  Change the ejb's attribute: archiveName.

Using seam 2.2, eclipse 3.5.2, m2eclipse 0.10.  mvn cli: 2.2.1, jboss tools: 3.0.1 (I think jboss maven integration plugin is required)


Result has some slight differences from seam-gen project which were not requirements:
*) separate concerns into parent and aggregate projects.
 The parent's concern is dependency management (and other common pom settings like java target). The aggregator's concern is to aggregate the modules and define build workflow.  This difference may seem big, but the difference in the build output is nil.


Using eclipse, m2eclipse, maven, seam, wst together:
*) eclipse deploys the web app with context containig version: e.g., localhost/seamproject-0.0.1-SNAPSHOT.  outside of eclipse, maven builds/deploys the web app without the version: e.g., localhost/seamproject.
*) eclipse state interferes.  At times, necessary to run: clean project.  At times, necessary to remove/add jboss server (or just restart it).  Learned to remove/add ear from server frequently.

Here is approach I took to create seam archetype:

1) use jboss tools in eclipse to create a maven project
  and normalize to maven conventions directory structure,
  and change java target/source to 1.6,
  and artifact naming changes (groupid),
  and retain maven version numbers in module names,
  and separate project aggregation from dependency management (parent),
  and build and deploy, as maven standalone project, without eclipse.

2) test 'import maven project' (to verify round-trip success)
  delete the jboss-tool generated seam project
  remove eclipse files (.classpath, .project, .settings) from normalized maven project
  import the normalized maven project
  inspect facets
  build and deploy.

3) create archetype from project
  and change some tempate code
  and change some filenaming and other customization
  and test with archetype:generate and import maven project to eclipse


