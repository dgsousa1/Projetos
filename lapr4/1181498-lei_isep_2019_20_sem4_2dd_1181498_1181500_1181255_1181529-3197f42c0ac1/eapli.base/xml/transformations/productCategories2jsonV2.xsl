<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xpath-default-namespace="http://www.dei.isep.ipp.pt/lprog/sffm">
	<xsl:output method="text" encoding="UTF-8"/>	
	
	<xsl:template match="sffm">
		{"ProductCategories":[
		<xsl:for-each select="productCategories/productCategory">
			<xsl:call-template name="show_cat">
				<xsl:with-param name="productCategory" select="."/>
			</xsl:call-template>		
		</xsl:for-each>
		
		]}
	</xsl:template>
	
	<xsl:template name="show_cat"><xsl:param name="productCategory"/><xsl:if test="not(position()=last())">
		{"category":"<xsl:value-of select="$productCategory"/>"},</xsl:if>
		<xsl:if test="position()=last()">
		{"category":"<xsl:value-of select="$productCategory"/>"}</xsl:if>
	</xsl:template>
	
	
</xsl:stylesheet>	