<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xpath-default-namespace="http://www.dei.isep.ipp.pt/lprog/sffm">
	<xsl:output method="html"/>
	<xsl:variable name="header">
		<tr bgcolor="#FF3300">
			<th/>
			<th style="font-family:'Georgia'">Manufacturing Code</th>
			<th style="font-family:'Georgia'">Commercial Code</th>
			<th style="font-family:'Georgia'">Brief Description</th>
			<th style="font-family:'Georgia'">Category</th>
			<th style="font-family:'Georgia'">Unit</th>
		</tr>
	</xsl:variable>
	
	<xsl:variable name="title">
		<h1 style="color:#000000;font-family:'Times new Roman';text-align:center;font-weight:strong">Product Catalog</h1>
		<h4 style="color:#000000;font-family:'Times new Roman';text-align:left;font-weight:strong">Showing max of 25 Products</h4>	
	</xsl:variable>
	
	<xsl:template match="sffm">
		<html>
			<body bgcolor="#FFFFFF">
				<xsl:copy-of select="$title"/>
				<h3 style="color:#000000;font-family:'Times new Roman'">Number Of Products: <font color="#FF0000"><xsl:value-of select="count(productCatalog/products/product)"/></font></h3>
				<table border="1" bordercolor="#B4B4B4" >
					<xsl:copy-of select="$header" />
					<xsl:for-each select="productCatalog/products/product">
						<xsl:sort select="productCategory"/>
						<xsl:if test="not(position() > 25)">
							<tr bgcolor="#B4B4B4">
								<td style="font-family:'Georgia'"> <xsl:value-of select="position()"/>. </td>
								<td style="font-family:'Georgia'"><xsl:value-of select="productCode/manufacturingCode"/></td>
								<td style="font-family:'Georgia'"><xsl:value-of select="productCode/commercialCode"/></td>
								<td style="font-family:'Georgia'"><xsl:value-of select="productDescription/briefDescription"/></td>
								<td style="font-family:'Georgia'"><xsl:value-of select="productCategory"/></td>	
								<td style="font-family:'Georgia'"><xsl:value-of select="rawMaterial/unit"/></td>
							</tr>
						</xsl:if>
					</xsl:for-each>
				</table>
			</body>
		</html>
	</xsl:template>
	
	
</xsl:stylesheet>