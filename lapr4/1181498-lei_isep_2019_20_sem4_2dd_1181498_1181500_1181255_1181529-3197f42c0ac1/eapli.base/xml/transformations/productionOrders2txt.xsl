<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xpath-default-namespace="http://www.dei.isep.ipp.pt/lprog/sffm">
	<xsl:output method="text" encoding="UTF-8"/>
	<xsl:variable name="divider">
		#################################
	</xsl:variable>
	<xsl:variable name="divider2">
		-----------------------
	</xsl:variable>
	
	
	<xsl:template match="sffm">
		Number of Production Orders - <xsl:value-of select="count(productionOrders/productionOrder)"/>
		<xsl:apply-templates select="productionOrders/productionOrder"/>
	</xsl:template>
	
	<xsl:template match="productionOrder">
		<xsl:copy-of select="$divider"/>
		Code - <xsl:value-of select="@code"/>
		Issued On - <xsl:value-of select="issueDate"/>
		Status - <xsl:value-of select="@status"/>
		<xsl:if test="materialsConsumed/materialConsumed">
		<xsl:copy-of select="$divider2"/>
		Materials Consumed:
		<xsl:apply-templates select="materialsConsumed/materialConsumed"/>
		</xsl:if>
		<xsl:copy-of select="$divider2"/>
		Schedule:	
		<xsl:apply-templates select="productionOrderSchedule"/>
		<xsl:copy-of select="$divider2"/>
		Result Product:
		<xsl:apply-templates select="productResult"/>
		<xsl:copy-of select="$divider2"/>
		Associated Commissions:
		<xsl:apply-templates select="associatedCommissions/commission"/>	
	</xsl:template>
	
	<xsl:template match="materialConsumed">
		Material - <xsl:value-of select="rawMaterial/@name"/>
		Quantity - <xsl:value-of select="@quantity"/>  <xsl:value-of select="rawMaterial/unit" />
	</xsl:template>
	
	<xsl:template match="commission">
		Commission - "<xsl:value-of select="."/>";
	</xsl:template>
	
	<xsl:template match="productResult">
		Product - <xsl:value-of select="product/productDescription/briefDescription"/>
		Quantity - <xsl:value-of select="@quantity"/> <xsl:value-of select="product/rawMaterial/unit"/>
		Manufacturing Code - <xsl:value-of select="product/productCode/manufacturingCode"/>
		Commercial Code - <xsl:value-of select="product/productCode/commercialCode"/>
		Category - <xsl:value-of select="product/productCategory"/>
		Complete Description - <xsl:value-of select="product/productDescription/completeDescription"/>
	</xsl:template>
	
	<xsl:template match="productionOrderSchedule">
		Expected start of execution - <xsl:value-of select="expectedStartOfExecution"/>
		<xsl:if test="realStartOfExecution">
		Real start of execution - <xsl:value-of select="realStartOfExecution"/></xsl:if>
		<xsl:if test="not(realStartOfExecution)">
		Real start of execution - N/A</xsl:if>
		<xsl:if test="endExecution">
		End of execution - <xsl:value-of select="endExecution"/></xsl:if>
		<xsl:if test="not(endExecution)">
		End of execution - N/A</xsl:if>
		Gross execution time - <xsl:value-of select="grossExecutionTime"/>
		Effective execution time - <xsl:value-of select="effectiveExecutionTime"/>
	</xsl:template>
	

</xsl:stylesheet>
