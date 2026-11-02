<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml" encoding="UTF-8" indent="yes"/>

    <xsl:key name="by-origin" match="Device" use="Origin"/>

    <xsl:template match="/Devices">
        <Origins>
            <xsl:for-each select="Device[generate-id() = generate-id(key('by-origin', Origin)[1])]">
                <xsl:sort select="Origin"/>
                <Origin name="{Origin}">
                    <xsl:for-each select="key('by-origin', Origin)">
                        <xsl:sort select="Name"/>
                        <xsl:copy-of select="."/>
                    </xsl:for-each>
                </Origin>
            </xsl:for-each>
        </Origins>
    </xsl:template>
</xsl:stylesheet>