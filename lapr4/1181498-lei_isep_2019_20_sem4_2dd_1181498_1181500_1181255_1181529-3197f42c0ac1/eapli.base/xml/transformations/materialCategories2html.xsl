<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xpath-default-namespace="http://www.dei.isep.ipp.pt/lprog/sffm">
	<xsl:output method="html"/>
	
	<xsl:template match="sffm">
		<html>
			<body bgcolor="#FFFFFF">
				<h1 style="color:#000000;font-family:'Times new Roman';text-align:center;font-weight:strong">Material Categories</h1>
				<h3 style="color:#000000;font-family:'Times new Roman'">Number Of Categories: <font color="#FF0000"><xsl:value-of select="count(materialCategories/materialCategory)"/></font></h3>
				<table border="2" bordercolor="#B4B4B4">
					<tr bgcolor="#FF3300">
						<th/>
						<th style="font-family:'Georgia'">Category Code</th>
						<th style="font-family:'Georgia'">Category Description</th>
					</tr>
					<xsl:for-each select="materialCategories/materialCategory">
						<tr bgcolor="#B4B4B4">
							<td style="font-family:'Georgia'"><xsl:value-of select="position()"/></td>
							<td style="font-family:'Georgia'"><xsl:value-of select="@code"/></td>
							<td style="font-family:'Georgia'"><xsl:value-of select="categoryDescription/text()"/></td>
						</tr>
					</xsl:for-each>
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
