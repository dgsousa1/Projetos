<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xpath-default-namespace="http://www.dei.isep.ipp.pt/lprog/sffm">
	<xsl:output method="text" encoding="UTF-8"/>	
	
	<xsl:template match="sffm">
		<xsl:apply-templates select="storageAreas"/>
	</xsl:template>	
	
	
	<xsl:template match="storageAreas">
	{"StorageAreas": [<xsl:for-each select="storageArea">
		"description": "<xsl:value-of select="storageAreaDescription"/>,
		"batches" : [<xsl:for-each select="batches/batch">
				<xsl:if test="not(position()=last())">
				"batch" : {
					"Code":"<xsl:value-of select="@batchCode"/>",
					"CreationDate":"<xsl:value-of select="creationDateTime"/>",
					"MaterialName":"<xsl:value-of select="measuredRawMaterial/rawMaterial/@name"/>",
					"MaterialQuantity":"<xsl:value-of select="measuredRawMaterial/@quantity"/>",
					"Unit":"<xsl:value-of select="measuredRawMaterial/rawMaterial/unit"/>"
					},</xsl:if>	
				<xsl:if test="position()=last()">
				"batch" : {
					"Code":"<xsl:value-of select="@batchCode"/>",
					"CreationDate":"<xsl:value-of select="creationDateTime"/>",
					"MaterialName":"<xsl:value-of select="measuredRawMaterial/rawMaterial/@name"/>",
					"MaterialQuantity":"<xsl:value-of select="measuredRawMaterial/@quantity"/>",
					"Unit":"<xsl:value-of select="measuredRawMaterial/rawMaterial/unit"/>"
					}</xsl:if>	
				]
			</xsl:for-each>
			</xsl:for-each>
	]}	
	</xsl:template>
</xsl:stylesheet>