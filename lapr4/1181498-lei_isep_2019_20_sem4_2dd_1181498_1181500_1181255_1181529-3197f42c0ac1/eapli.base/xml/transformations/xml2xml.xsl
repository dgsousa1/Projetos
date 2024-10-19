<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xpath-default-namespace="http://www.dei.isep.ipp.pt/lprog/sffm">
	<xsl:output method="xml" encoding="UTF-8" indent="yes"/>	
	
	<xsl:template match="/">
		<sffm>
			<xsl:apply-templates select="sffm/materialCategories"/>
			
			<xsl:apply-templates select="sffm/productCategories"/>
			
			<xsl:apply-templates select="sffm/materialCatalog"/>
			
			<xsl:apply-templates select="sffm/productCatalog"/>
			
			<xsl:apply-templates select="sffm/billsOfMaterials"/>
			
			<xsl:apply-templates select="sffm/productionOrders"/>
			
			<xsl:apply-templates select="sffm/productionLines"/>
			
			<xsl:apply-templates select="sffm/storageAreas"/>
			
			<xsl:apply-templates select="sffm/stockMovements"/>
		</sffm>
	</xsl:template>
	
	<xsl:template match="stockMovements">
		<stockMovements>
			<xsl:attribute name="quantity">
				<xsl:value-of select="count(stockMovement)"/>
			</xsl:attribute>
			<xsl:apply-templates select="stockMovement"/>
		</stockMovements>
	</xsl:template>
	
	<xsl:template match="stockMovement">
		<stockMovement>
			<xsl:attribute name="number">
				<xsl:value-of select="position()"/>
			</xsl:attribute>	
			<direction><xsl:value-of select="@direction"/></direction>
			<movementDate><xsl:value-of select="movementDateTime"/></movementDate>	
			<description><xsl:value-of select="storageAreaDescription"/></description>
			<xsl:apply-templates select="batches"/>	
		</stockMovement>
	</xsl:template>
	
	<xsl:template match="storageAreas">
		<storageAreas>
			<xsl:attribute name="quantity">
				<xsl:value-of select="count(storageArea)"/>
			</xsl:attribute>
			<xsl:apply-templates select="storageArea"/>
		</storageAreas>
	</xsl:template>
	
	<xsl:template match="storageArea">
		<storageArea>
			<xsl:attribute name="number">
				<xsl:value-of select="position()"/>
			</xsl:attribute>	
			<description><xsl:value-of select="storageAreaDescription"/></description>
			<xsl:apply-templates select="batches"/>
		</storageArea>	
	</xsl:template>
	
	<xsl:template match="batches">
		<batches>
			<xsl:attribute name="quantity">
				<xsl:value-of select="count(batch)"/>
			</xsl:attribute>
			<xsl:apply-templates select="batch"/>
		</batches>	
	</xsl:template>
	
	<xsl:template match="batch">
		<batch>
			<xsl:attribute name="number">
				<xsl:value-of select="position()"/>
			</xsl:attribute>	
			<batchCode><xsl:value-of select="@batchCode"/></batchCode>
			<creationDate><xsl:value-of select="creationDateTime"/></creationDate>
			<xsl:apply-templates select="measuredRawMaterial"/>		
		</batch>			
	</xsl:template>
	
	<xsl:template match="measuredRawMaterial">
		<measuredRawMaterial>
			<quantity><xsl:value-of select="@quantity"/></quantity>
			<name><xsl:value-of select="rawMaterial/@name"/></name>
			<unit><xsl:value-of select="rawMaterial/unit"/></unit>
		</measuredRawMaterial>
	</xsl:template>
		
	<xsl:template match="productionLines">
		<productionLines>
			<xsl:attribute name="quantity">
				<xsl:value-of select="count(productionLine)"/>
			</xsl:attribute>
			<xsl:apply-templates select="productionLine"/>
		</productionLines>
	</xsl:template>
	
	<xsl:template match="productionLine">
		<productionLine>
			<xsl:attribute name="number">
				<xsl:value-of select="position()"/>
			</xsl:attribute>	
		<id><xsl:value-of select="@id"/></id>
		<xsl:apply-templates select="machines"/>
		<xsl:apply-templates select="associatedProductionOrder"/>			
		</productionLine>
	</xsl:template>
	
	<xsl:template match="associatedProductionOrder">
		<productionOrder>
			<status><xsl:value-of select="productionOrderStatus"/></status>
			<code><xsl:value-of select="@code"/></code>
			<xsl:apply-templates select="materialsConsumed"/>
			<xsl:apply-templates select="productionOrderSchedule"/>
			<xsl:apply-templates select="productResult"/>
			<issueDate><xsl:value-of select="issueDate"/></issueDate>
			<xsl:apply-templates select="associatedCommissions"/>
		</productionOrder>
	</xsl:template>
	
	<xsl:template match="machines">
		<machines>
			<xsl:attribute name="quantity">
				<xsl:value-of select="count(machine)"/>
			</xsl:attribute>
			<xsl:apply-templates select="machine"/>
		</machines>
	
	</xsl:template>
	
	<xsl:template match="machine">
		<machine>
			<xsl:attribute name="number">
				<xsl:value-of select="position()"/>
			</xsl:attribute>	
			<serialNumber><xsl:value-of select="@serialNumber"/></serialNumber>
			<machineModel>
				<name><xsl:value-of select="machineModel/@name"/></name>
				<description><xsl:value-of select="machineModel/description"/></description>
				<brand><xsl:value-of select="machineModel/machineBrand"/></brand>
			</machineModel>
		</machine>		
	</xsl:template>	
	
	<xsl:template match="productionOrders">
		<productionOrders>
			<xsl:attribute name="quantity">
				<xsl:value-of select="count(productionOrder)"/>
			</xsl:attribute>
			<xsl:apply-templates select="productionOrder"/>
		</productionOrders>	
	</xsl:template>
	
	<xsl:template match="productionOrder">
		<productionOrder>
			<xsl:attribute name="number">
				<xsl:value-of select="position()"/>
			</xsl:attribute>	
			<status><xsl:value-of select="productionOrderStatus"/></status>
			<code><xsl:value-of select="@code"/></code>
			<xsl:apply-templates select="materialsConsumed"/>
			<xsl:apply-templates select="productionOrderSchedule"/>
			<xsl:apply-templates select="productResult"/>
			<issueDate><xsl:value-of select="issueDate"/></issueDate>
			<xsl:apply-templates select="associatedCommissions"/>
		</productionOrder>
	</xsl:template>
	
	<xsl:template match="associatedCommissions">
		<commissions>
			<xsl:attribute name="quantity">
				<xsl:value-of select="count(commission)"/>
			</xsl:attribute>
			<xsl:apply-templates select="commission"/>
		</commissions>
	</xsl:template>
	
	<xsl:template match="commission">
		<commission>
			<xsl:attribute name="number">
				<xsl:value-of select="position()"/>
			</xsl:attribute>	
			<xsl:value-of select="."/>		
		</commission>
	</xsl:template>
	
	<xsl:template match="productResult">
		<productResult>
			<xsl:attribute name="quantity">
				<xsl:value-of select="@quantity"/>
			</xsl:attribute>	
			<productCodes>
				<xsl:attribute name="manufacturingCode">
					<xsl:value-of select="product/productCode/manufacturingCode"/>
				</xsl:attribute>	
				<xsl:attribute name="commercialCode">
					<xsl:value-of select="product/productCode/commercialCode"/>
				</xsl:attribute>
			</productCodes>		
			<briefDescription><xsl:value-of select="product/productDescription/briefDescription"/></briefDescription>
			<completeDescription><xsl:value-of select="product/productDescription/completeDescription"/></completeDescription>
			<category><xsl:value-of select="product/productCategory"/></category>
			<rawMaterial>
				<xsl:attribute name="name">
					<xsl:value-of select="product/rawMaterial/@name"/>
				</xsl:attribute>	
				<xsl:attribute name="unit">
					<xsl:value-of select="product/rawMaterial/unit"/>
				</xsl:attribute>	
			</rawMaterial>
		</productResult>	
	</xsl:template>
	
	<xsl:template match="productionOrderSchedule">
		<schedule>
			<startOfExecution>
				<expected><xsl:value-of select="expectedStartOfExecution"/></expected>
				<real><xsl:value-of select="realStartOfExecution"/></real>
			</startOfExecution>
			<endOfExecution><xsl:value-of select="endExecution"/></endOfExecution>
			<times>
				<grossExecutionTime><xsl:value-of select="grossExecutionTime"/></grossExecutionTime>
				<effectiveExecutionTime><xsl:value-of select="effectiveExecutionTime"/></effectiveExecutionTime>
			</times>
		</schedule>	
	</xsl:template>
	
	<xsl:template match="materialsConsumed">
		<materialsConsumed>
			<xsl:apply-templates select="materialConsumed"/>
		</materialsConsumed>
	</xsl:template>	
	
	<xsl:template match="materialConsumed">
		<materialConsumed>
			<xsl:attribute name="number">
				<xsl:value-of select="position()"/>
			</xsl:attribute>	
			<xsl:attribute name="name">
				<xsl:value-of select="rawMaterial/@name"/>
			</xsl:attribute>	
		<quantity><xsl:value-of select="@quantity"/></quantity>
		<unit><xsl:value-of select="rawMaterial/unit"/></unit>	
		</materialConsumed>			
	</xsl:template>
	
	<xsl:template match="billsOfMaterials">
		<billsOfMaterials>
			<xsl:attribute name="quantity">
				<xsl:value-of select="count(billOfMaterials)"/>
			</xsl:attribute>
			<xsl:apply-templates select="billOfMaterials"/>
		</billsOfMaterials>	
	</xsl:template>
	
	<xsl:template match="billOfMaterials">
		<billOfMaterials>
			<xsl:attribute name="number">
				<xsl:value-of select="position()"/>
			</xsl:attribute>	
			<issueDate><xsl:value-of select="issueDate"/></issueDate>
			<xsl:apply-templates select="finishedProduct"/>
		</billOfMaterials>
	</xsl:template>

	<xsl:template match="finishedProduct">
		<finishedProduct>
			<productCodes>
				<xsl:attribute name="manufacturingCode">
					<xsl:value-of select="productCode/manufacturingCode"/>
				</xsl:attribute>	
				<xsl:attribute name="commercialCode">
					<xsl:value-of select="productCode/commercialCode"/>
				</xsl:attribute>
			</productCodes>		
			<briefDescription><xsl:value-of select="productDescription/briefDescription"/></briefDescription>
			<completeDescription><xsl:value-of select="productDescription/completeDescription"/></completeDescription>
			<category><xsl:value-of select="productCategory"/></category>
			<rawMaterial>
				<xsl:attribute name="name">
					<xsl:value-of select="rawMaterial/@name"/>
				</xsl:attribute>	
				<xsl:attribute name="unit">
					<xsl:value-of select="rawMaterial/unit"/>
				</xsl:attribute>	
			</rawMaterial>
		</finishedProduct>	
	</xsl:template>
	
	<xsl:template match="productCatalog">
		<productCatalog>
			<xsl:attribute name="quantity">
				<xsl:value-of select="count(products/product)"/>
			</xsl:attribute>
			<xsl:apply-templates select="products/product"/> 
		</productCatalog>
	</xsl:template>
	
	<xsl:template match="product">
		<product>
			<xsl:attribute name="number">
				<xsl:value-of select="position()"/>
			</xsl:attribute>	
			<productCodes>
				<xsl:attribute name="manufacturingCode">
					<xsl:value-of select="productCode/manufacturingCode"/>
				</xsl:attribute>	
				<xsl:attribute name="commercialCode">
					<xsl:value-of select="productCode/commercialCode"/>
				</xsl:attribute>
			</productCodes>		
			<briefDescription><xsl:value-of select="productDescription/briefDescription"/></briefDescription>
			<completeDescription><xsl:value-of select="productDescription/completeDescription"/></completeDescription>
			<category><xsl:value-of select="productCategory"/></category>
			<rawMaterial>
				<xsl:attribute name="name">
					<xsl:value-of select="rawMaterial/@name"/>
				</xsl:attribute>	
				<xsl:attribute name="unit">
					<xsl:value-of select="rawMaterial/unit"/>
				</xsl:attribute>	
			</rawMaterial>
		</product>	
	</xsl:template>
	
	<xsl:template match="materialCatalog">
		<materialCatalog>
			<xsl:attribute name="quantity">
				<xsl:value-of select="count(materials/material)"/>
			</xsl:attribute>
			<xsl:apply-templates select="materials/material"/> 
		</materialCatalog>	
	</xsl:template>
	
	<xsl:template match="material">
		<material>
			<xsl:attribute name="number">
				<xsl:value-of select="position()"/>
			</xsl:attribute>	
			<code><xsl:value-of select="materialCode/@code"/></code>
			<name><xsl:value-of select="materialCode/materialName"/></name>
			<category><xsl:value-of select="materialCategoryCode"/></category>
			<xsl:apply-templates select="technicalFile"/>
			<rawMaterial>
				<xsl:attribute name="name">
					<xsl:value-of select="rawMaterial/@name"/>
				</xsl:attribute>	
				<xsl:attribute name="unit">
					<xsl:value-of select="rawMaterial/unit"/>
				</xsl:attribute>	
			</rawMaterial>
		</material>
	</xsl:template>
	
	<xsl:template match="technicalFile">
		<technicalFile>
			<path><xsl:value-of select="filePath"/></path>
			<name><xsl:value-of select="fileName"/></name>
		</technicalFile>
	</xsl:template>
	
	<xsl:template match="productCategories">
		<productCategories>
			<xsl:attribute name="quantity">
				<xsl:value-of select="count(productCategory)"/>
			</xsl:attribute>
			<xsl:apply-templates select="productCategory"/>
		</productCategories>
	</xsl:template>	
	
	<xsl:template match="productCategory">
		<productCategory>
			<xsl:attribute name="number">
				<xsl:value-of select="position()"/>
			</xsl:attribute>
			<xsl:value-of select="."/>
		</productCategory>
	</xsl:template>
	
	<xsl:template match="materialCategories">
		<materialCategories>
			<xsl:attribute name="quantity">
				<xsl:value-of select="count(materialCategory)"/>
			</xsl:attribute>
			<xsl:apply-templates select="materialCategory"/>
		</materialCategories>
	</xsl:template>	
	
	<xsl:template match="materialCategory">
		<materialCategory>
			<code><xsl:value-of select="@code"/></code>
			<description><xsl:value-of select="categoryDescription"/></description>
		</materialCategory>
	</xsl:template>
	
	
</xsl:stylesheet>