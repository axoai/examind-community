package com.examind.process.admin.renderedpyramid;

import com.examind.process.admin.AdminProcessRegistry;
import static com.examind.process.admin.renderedpyramid.AbstractPyramidProcess.BUNDLE_LOCATION;
import java.util.ArrayList;
import java.util.List;
import org.apache.sis.parameter.ParameterBuilder;
import org.apache.sis.storage.DataStoreException;
import org.apache.sis.util.iso.ResourceInternationalString;
import org.geotoolkit.data.multires.MultiResolutionResource;
import org.geotoolkit.data.multires.Pyramid;
import org.geotoolkit.data.multires.Pyramids;
import org.geotoolkit.data.multires.TileGenerator;
import org.geotoolkit.display2d.MapContextTileGenerator;
import org.geotoolkit.image.interpolation.InterpolationCase;
import org.geotoolkit.map.CoverageMapLayer;
import org.geotoolkit.map.MapContext;
import org.geotoolkit.map.MapLayer;
import org.geotoolkit.process.ProcessException;
import org.geotoolkit.processing.AbstractProcess;
import org.geotoolkit.processing.AbstractProcessDescriptor;
import org.geotoolkit.processing.ForwardProcessListener;
import org.geotoolkit.storage.coverage.CoverageTileGenerator;
import org.geotoolkit.storage.coverage.GridCoverageResource;
import org.opengis.parameter.ParameterDescriptor;
import org.opengis.parameter.ParameterDescriptorGroup;
import org.opengis.parameter.ParameterValueGroup;
import org.springframework.stereotype.Component;

/**
 *
 * @author Johann Sorel (Geomatys)
 */
@Component
public class PyramidProcess extends AbstractProcessDescriptor {

    protected static final ParameterDescriptor<MapContext> MAPCONTEXT;
    protected static final ParameterDescriptor<MultiResolutionResource> RESOURCE;
    protected static final ParameterDescriptor<InterpolationCase> INTERPOLATION;
    protected static final ParameterDescriptor<String> MODE;


    private static final ParameterDescriptorGroup INPUT;

    private static final ParameterDescriptorGroup OUTPUT;

    static {
        final ParameterBuilder builder = new ParameterBuilder();


        MAPCONTEXT = builder.addName("mapcontext")
                .setRequired(true)
                .create(MapContext.class, null);

        RESOURCE = builder.addName("resource")
                .setRequired(true)
                .create(MultiResolutionResource.class, null);

        INTERPOLATION = builder.addName("interpolation")
                .setRequired(true)
                .create(InterpolationCase.class, InterpolationCase.NEIGHBOR);

        MODE = builder.addName("mode")
                .setRequired(true)
                .createEnumerated(String.class, new String[]{"data","rgb"},"data");


        INPUT = builder.addName("input").createGroup(MAPCONTEXT, RESOURCE, INTERPOLATION, MODE);
        OUTPUT = builder.addName("output").createGroup();
    }

    public PyramidProcess() {
        super("gen-pyramid", AdminProcessRegistry.IDENTIFICATION,
                new ResourceInternationalString(BUNDLE_LOCATION, "gen.description"),
                new ResourceInternationalString(BUNDLE_LOCATION, "gen.title"), INPUT, OUTPUT);
    }

    @Override
    public org.geotoolkit.process.Process createProcess(ParameterValueGroup input) {
        return new Processor(input);
    }

    private class Processor extends AbstractProcess {

        public Processor(ParameterValueGroup input) {
            super(PyramidProcess.this, input);
        }

        @Override
        protected void execute() throws ProcessException {

            final MapContext context = inputParameters.getMandatoryValue(MAPCONTEXT);
            final MultiResolutionResource resource = inputParameters.getMandatoryValue(RESOURCE);
            final InterpolationCase interpolation = inputParameters.getMandatoryValue(INTERPOLATION);
            final String mode = inputParameters.getMandatoryValue(MODE);

            final TileGenerator generator;
            switch (mode) {
                case "rgb" : generator = new MapContextTileGenerator(context, null); break;
                case "data" : {
                    final List<GridCoverageResource> resources = new ArrayList<>();
                    for (MapLayer layer : context.layers()) {
                        if (layer instanceof CoverageMapLayer) {
                            final GridCoverageResource candidate = ((CoverageMapLayer) layer).getCoverageReference();
                            resources.add(candidate);
                        }
                    }

                    if (resources.size() != 1) {
                        throw new ProcessException("MapContext must contain a single coverage layer ", this);
                    }

                    CoverageTileGenerator ctg;
                    try {
                        ctg = new CoverageTileGenerator(resources.get(0));
                    } catch (DataStoreException ex) {
                        throw new ProcessException(ex.getMessage(), this, ex);
                    }
                    ctg.setInterpolation(interpolation);
                    generator = ctg;
                } break;
                default : throw new ProcessException("Unexpected pyramid mode "+mode, this);
            }

            try {
                for (Pyramid pyramid : Pyramids.getPyramids(resource)) {
                    final ForwardProcessListener fp = new ForwardProcessListener(this, 1, 99);
                    generator.generate(pyramid, null, null, fp);
                }
            } catch (DataStoreException | InterruptedException ex) {
                throw new ProcessException(ex.getMessage(), this, ex);
            }

        }

    }

}
