<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xpath-default-namespace="http://www.dei.isep.ipp.pt/lprog/sffm">
	<xsl:output method="text" encoding="UTF-8"/>
	<xsl:template match="sffm">
		<xsl:apply-templates select="materialCategories"/>
	</xsl:template>	
	
	<xsl:template match="materialCategories">
	{"MaterialCategories":[<xsl:for-each select="materialCategory"><xsl:if test="not(position()=last())">
		{"code":"<xsl:value-of select="@code"/>", "description":"<xsl:value-of select="categoryDescription"/>"},</xsl:if>
		<xsl:if test="position()=last()">
		{"code":"<xsl:value-of select="@code"/>", "description":"<xsl:value-of select="categoryDescription"/>"}</xsl:if>
		</xsl:for-each>	
	]}	
	
	</xsl:template>
	
	
	
</xsl:stylesheet>
