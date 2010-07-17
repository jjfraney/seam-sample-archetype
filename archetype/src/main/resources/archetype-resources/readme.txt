#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
TODO: remove username/password from datasource files

Goals:
a) an archetype to generate the same project of seam-tools with jboss maven support.
b) a seam maven project that conforms to maven's directory structure, not eclipse's.

Non-Goal:
a) generate hibernate console config
b) generate jboss' datasource deployable

Issues:
*) let maven generate the application.xml file or not.
*) on import, an m2eclipse bug: uses *.ejb not *.jar for ejb module.  Need to manually edit .settings/org.eclipse.wst.common.component BEFORE deploy.  Change the ejb's attribute: archiveName.

Using seam 2.2, eclipse 3.5.2, m2eclipse 0.10.  mvn cli: 2.2.1, jboss tools: 3.0.1 (I think jboss maven integration plugin is required)


Result has some slight differences which were not requirements:
*) separate concerns into parent and aggregate projects.
 The parent's concern is dependency management (and other common pom settings like java target). The aggregator's concern is to aggregate the modules and define build workflow.  This difference may seem big, but the difference in the build output is nil.
*) eclipse deploys the web app with context containig version: e.g., localhost/seamproject-${version}.  outside of eclipse, maven builds/deploys the web app without the version: e.g., localhost/seamproject.
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


1) generate the seam project datasource
2) generate the hibernate console
3) specify seam dependencies management in project parent
4) create project aggregate (in project parent)
5) project name appearances

in web)

  webapp/WEB-INF
	components.xml
            <managed-persistence-context persistence-unit-jndi-name..../>
            <core:init jndi-pattern... />
              fix: define project property: jndi-pattern
	web.xml
            <display-name>
	jboss-web.xml
	    <loader-repository>
  main/webapp/layout/template.xhtml
     projectName for menu template

in ejb)
  *-ejb.launch
    to support hibernate console
    possible fix: remove, do not support
  hibernate-console.properties
    possible fix: remove, do not support
    to support hibernate console
  main/resource/META-INF/persistence.xml
    fix: use property: persistence-unit-name (defined in parent pom)
    <persistence-unit> name
    <jta-data-source>
    <property name="jboss.entity.manager.factory.jndi.name"  .... />
  main/resource/components.properties
    fix: not sure this is useed at web app runtime, maybe remove
    jndi-pattern


in ear)
  main/resource/META-INF
    jboss-app.xml
        <loader-repository>
        fix: using ${symbol_dollar}{project.artifactId}
    application.xml
        fix: maybe generate by maven's plugin
        modules and web context root

