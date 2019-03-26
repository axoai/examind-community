/*
 *    Constellation - An open source and standard compliant SDI
 *    http://www.constellation-sdi.org
 *
 * Copyright 2014 Geomatys.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.constellation.metadata.index.analyzer;

import org.apache.lucene.search.Filter;
import org.constellation.metadata.index.generic.GenericIndexer;
import org.geotoolkit.lucene.analysis.standard.ClassicAnalyzer;
import org.geotoolkit.lucene.filter.SpatialQuery;
import org.geotoolkit.lucene.index.LuceneIndexSearcher;
import org.geotoolkit.nio.IOUtilities;
import org.junit.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import org.geotoolkit.index.LogicalFilterType;
import javax.annotation.PostConstruct;
import static org.constellation.metadata.index.analyzer.AbstractAnalyzerTest.indexSearcher;
import org.geotoolkit.index.tree.manager.SQLRtreeManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//Junit dependencies

/**
 *
 * @author Guilhem Legal (Geomatys)
 */
public class GeotkClassicAnalyzerTest extends AbstractAnalyzerTest {

    private static Path configDirectory = Paths.get("GeotkClassicAnalyzerTest");

    private static boolean configured = false;
    
    @PostConstruct
    public void setUpClass() throws Exception {
        if (!configured) {
            IOUtilities.deleteRecursively(configDirectory);
            List<Object> object = fillTestData();
            GenericIndexer indexer = new GenericIndexer(object, null, configDirectory, "", new ClassicAnalyzer(), Level.FINER, true);
            indexer.destroy();
            indexSearcher          = new LuceneIndexSearcher(configDirectory, "", new ClassicAnalyzer(), true);
            indexSearcher.setLogLevel(Level.FINER);
            configured = true;
        }
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        if (indexSearcher != null) {
            indexSearcher.destroy();
        }
        SQLRtreeManager.removeTree(indexSearcher.getFileDirectory());
        IOUtilities.deleteRecursively(configDirectory);
    }

    /**
     * Test simple lucene search.
     *
     * @throws java.lang.Exception
     */
    @Test
    @Override
    public void simpleSearchTest() throws Exception {
        super.simpleSearchTest();
    }

     /**
     * Test simple lucene search.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void wildCharSearchTest() throws Exception {
        Filter nullFilter   = null;
        String resultReport = "";

        /**
         * Test 1 simple search: title = title1
         */
        SpatialQuery spatialQuery = new SpatialQuery("Title:90008411*", nullFilter, LogicalFilterType.AND);
        Set<String> result = indexSearcher.doSearch(spatialQuery);

        for (String s: result) {
            resultReport = resultReport + s + '\n';
        }

        logger.log(Level.FINER, "wildCharSearch 1:\n{0}", resultReport);

        Set<String> expectedResult = new LinkedHashSet<>();
        expectedResult.add("42292_5p_19900609195600");
        expectedResult.add("42292_9s_19900610041000");

        assertEquals(expectedResult, result);

        /**
         * Test 2 wildChar search: abstract LIKE *NEDIPROD*
         */
        spatialQuery = new SpatialQuery("abstract:*NEDIPROD*", nullFilter, LogicalFilterType.AND);
        result = indexSearcher.doSearch(spatialQuery);

        resultReport = "";
        for (String s: result) {
            resultReport = resultReport + s + '\n';
        }

        logger.log(Level.FINER, "wildCharSearch 2:\n{0}", resultReport);

        expectedResult = new LinkedHashSet<>();
        expectedResult.add("42292_5p_19900609195600");

        assertEquals(expectedResult, result);

        /**
         * Test 3 wildChar search: title like *.ctd
         */
        resultReport = "";
        spatialQuery = new SpatialQuery("Title:*.ctd", nullFilter, LogicalFilterType.AND);
        result       = indexSearcher.doSearch(spatialQuery);

        for (String s: result) {
            resultReport = resultReport + s + '\n';
        }

        logger.log(Level.FINER, "wildCharSearch 3:\n{0}", resultReport);

        // ISSUE here the . is removed at the idexation
        assertTrue(result.contains("39727_22_19750113062500"));
        assertTrue(result.contains("40510_145_19930221211500"));
        assertTrue(result.contains("42292_5p_19900609195600"));
        assertTrue(result.contains("42292_9s_19900610041000"));



        /**
         * Test 4 wildCharSearch: abstract LIKE *onnees CTD NEDIPROD VI 120
         */
        spatialQuery = new SpatialQuery("abstract:(*onnees CTD NEDIPROD VI 120)", nullFilter, LogicalFilterType.AND);
        result = indexSearcher.doSearch(spatialQuery);

        resultReport = "";
        for (String s: result) {
            resultReport = resultReport + s + '\n';
        }

        logger.log(Level.FINER, "wildCharSearch 4:\n{0}", resultReport);

        expectedResult = new LinkedHashSet<>();
        expectedResult.add("42292_5p_19900609195600");

        //issues here it found
        assertEquals(expectedResult, result);

        /**
         * Test 5 wildCharSearch: Format LIKE *MEDATLAS ASCII*
         */
        spatialQuery = new SpatialQuery("Format:(*MEDATLAS ASCII*)", nullFilter, LogicalFilterType.AND);
        result = indexSearcher.doSearch(spatialQuery);

        resultReport = "";
        for (String s: result) {
            resultReport = resultReport + s + '\n';
        }

        logger.log(Level.FINER, "wildCharSearch 5:\n{0}", resultReport);

        expectedResult = new LinkedHashSet<>();
        expectedResult.add("42292_5p_19900609195600");
        expectedResult.add("42292_9s_19900610041000");
        expectedResult.add("39727_22_19750113062500");
        expectedResult.add("11325_158_19640418141800"); // >>  ISSUES This one shoudn't be there because it not in the same order => ASCII MEDATLAS
        expectedResult.add("40510_145_19930221211500");

        assertEquals(expectedResult, result);

    }

     /**
     * Test simple lucene search.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void wildCharUnderscoreSearchTest() throws Exception {
        Filter nullFilter   = null;
        String resultReport = "";

        /**
         * Test 1 simple search: title = title1
         */
        SpatialQuery spatialQuery = new SpatialQuery("identifier:*MDWeb_FR_SY*", nullFilter, LogicalFilterType.AND);
        Set<String> result = indexSearcher.doSearch(spatialQuery);

        for (String s: result) {
            resultReport = resultReport + s + '\n';
        }

        logger.log(Level.FINER, " wildCharUnderscoreSearch 1:\n{0}", resultReport);

        Set<String> expectedResult = new LinkedHashSet<>();
        expectedResult.add("MDWeb_FR_SY_couche_vecteur_258");


        assertEquals(expectedResult, result);

        /**
         * Test 2 simple search: title = identifier:Spot5-Cyprus-THX-IMAGERY3_ortho*
         */
        spatialQuery = new SpatialQuery("identifier:Spot5-Cyprus-THX-IMAGERY3_ortho*", nullFilter, LogicalFilterType.AND);
        result = indexSearcher.doSearch(spatialQuery);

        for (String s: result) {
            resultReport = resultReport + s + '\n';
        }

        logger.log(Level.FINER, " wildCharUnderscoreSearch 1:\n{0}", resultReport);

        expectedResult = new LinkedHashSet<>();
        expectedResult.add("Spot5-Cyprus-THX-IMAGERY3_ortho1"); 

        assertEquals(expectedResult, result);
    }

     /**
     * Test simple lucene date search.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void dateSearchTest() throws Exception {
        super.dateSearchTest();
    }

    /**
     * Test sorted lucene search.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void sortedSearchTest() throws Exception {
        super.sortedSearchTest();
    }

    /**
     *
     * Test spatial lucene search.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void spatialSearchTest() throws Exception {
        super.spatialSearchTest();
    }

    /**
     *
     * Test spatial lucene search.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void TermQueryTest() throws Exception {
        super.TermQueryTest();
    }
}