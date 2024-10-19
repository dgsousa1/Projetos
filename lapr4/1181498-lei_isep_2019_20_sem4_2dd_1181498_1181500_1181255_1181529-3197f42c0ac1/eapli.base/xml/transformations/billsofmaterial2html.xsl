<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="4.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xpath-default-namespace="http://www.dei.isep.ipp.pt/lprog/sffm">
	<xsl:output method="html"/>
		<xsl:template match="sffm">
			<html>		
				<body bgcolor="#FFFFFF">
					<h1 style="color:#111111;font-family:'Times new Roman';text-align:center;font-weight:strong">Bills Of Materials</h1>
					<h2 style="color:#000000;font-family:'Times new Roman'">Number of Bills Of Materials: <font color="#FF0000"><xsl:value-of select="count(billsOfMaterials/billOfMaterials)"/></font> </h2>
					<xsl:for-each select="billsOfMaterials/billOfMaterials">
						<h3 style="font-family:'Georgia';color:#FF6800"><font color="#FF4600"><xsl:value-of select="position()"/>.</font> Bill Of Material of Product: <font color="#000000">"<xsl:value-of select="finishedProduct/productDescription/briefDescription"/>"</font></h3>
						<h4 style="font-family:'Georgia';color:#5B5B5B">Issued On: <xsl:value-of select="issueDate"/></h4>
						<h4 style="font-family:'Georgia';color:#5B5B5B">Complete Description: <xsl:value-of select="finishedProduct/productDescription/completeDescription"/></h4>
						<h4 	style="font-family:'Georgia';color:#5B5B5B">With Commercial Code: "<xsl:value-of select="finishedProduct/productCode/commercialCode"/>" and Manufacturing Code: "<xsl:value-of select="finishedProduct/productCode/manufacturingCode"/>"</h4>	
						<h4 	style="font-family:'Georgia';color:#5B5B5B">Product Category: "<xsl:value-of select="finishedProduct/productCategory"/>"</h4>	
						<h4 	style="font-family:'Georgia';color:#5B5B5B">Unit: "<xsl:value-of select="finishedProduct/rawMaterial/unit"/>"</h4>	
						<h3 style="font-family:'Georgia';color:#000000">List Of Materials:</h3>
						<xsl:for-each select="listOfMaterials/measuredRawMaterial">
							<xsl:sort select="@quantity"/>
							<h4 	style="font-family:'Georgia';color:#5B5B5B">Material: "<xsl:value-of select="rawMaterial/@name"/>", <xsl:value-of select="@quantity"/>
							<xsl:value-of select="rawMaterial/unit"/></h4>	
						</xsl:for-each>
					</xsl:for-each>
				</body>
			</html>
		</xsl:template>	
</xsl:stylesheet>