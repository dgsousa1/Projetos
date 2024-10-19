<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="4.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xpath-default-namespace="http://www.dei.isep.ipp.pt/lprog/sffm">
	<xsl:output method="text" encoding="UTF-8"/>
	<xsl:variable name="divider">
		#################################
	</xsl:variable>
	<xsl:variable name="divider2">
		-----------------------
	</xsl:variable>
	
	
	<xsl:template match="sffm">
		Number of Materials - <xsl:value-of select="count(materialCatalog/materials/material)"/>
		<xsl:apply-templates select="materialCatalog/materials/material"/>
	</xsl:template>
	
	<xsl:template match="materialCode">
		Material - <xsl:value-of select="materialName"/>
		Code - <xsl:value-of select="@code"/>
	</xsl:template>
	
	<xsl:template match="material">
		<xsl:copy-of select="$divider"/>
		<xsl:apply-templates select="materialCode"/>
		Unit - <xsl:value-of select="rawMaterial/unit"/>
		<xsl:copy-of select="$divider2"/>
		Category - <xsl:value-of select="materialCategoryCode"/>
		<xsl:copy-of select="$divider2"/>
		<xsl:apply-templates select="technicalFile"/>	
	</xsl:template>

	<xsl:template match="technicalFile">
		Technical File 
		Name - <xsl:value-of select="fileName"/>
		Path - <xsl:value-of select="filePath"/>	
	</xsl:template>
	

</xsl:stylesheet>
