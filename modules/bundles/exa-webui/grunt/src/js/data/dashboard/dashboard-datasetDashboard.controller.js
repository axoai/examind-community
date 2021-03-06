angular.module('cstl-data-dashboard')
    .controller('DatasetDashboardController', DatasetDashboardController);

/**
 * @name DatasetDashboardController
 *
 * @param $scope
 * @param $q
 * @param $cookieStore
 * @param $modal
 * @param $location
 * @param CstlConfig
 * @param Growl
 * @param AppConfigService
 * @param {Examind} Examind
 * @param {Dataset} Dataset
 * @param {DatasetDashboard} DatasetDashboard
 * @constructor
 */
function DatasetDashboardController($scope, $q, $cookieStore, $modal, $location, CstlConfig, Growl, AppConfigService, Examind, Dataset, DatasetDashboard) {

    var self = this;

    // Indicates if "singleton" datasets must be displayed.
    self.showSingleton = CstlConfig['dataset.listing.show_singleton'];

    // Overview layer instance.
    self.preview = { layer: undefined, extent: undefined };

    Object.defineProperties(self,{
        selection : {
            enumerable: true,
            get: function() {
                return DatasetDashboard;
            }
        }
    });

    // Array of available ways to add a data.
    self.addDataWays = [
        {
            idHTML: 'uploadchoice',
            name: 'localFile',
            translateKey: 'label.file.local',
            defaultTranslateValue: 'Local file',
            bindFunction: function() { startDataImport('local', 'step1DataLocal', true); }
        },
        {
            idHTML: 'filesystemchoice',
            name: 'serverFile',
            translateKey: 'label.file.server',
            defaultTranslateValue: 'Server file',
            bindFunction: function() { startDataImport('server', 'step1DataServer', true); }
        },
        {
            idHTML: 'dbchoice',
            name: 'database',
            translateKey: 'label.file.db',
            defaultTranslateValue: 'Database',
            bindFunction: function() { startDataImport('database', 'step1Database', true); }
        }
    ];


    // Select/unselect a dataset/data.
    // TODO change me by two function setDataset and setData
    self.toggleSelect = function(dataset, data) {
        // The given dataset is good and is selected
        if (!angular.isObject(data) && self.isSelected(dataset)) {
            self.selection.data = null;
            self.selection.dataset = null;
        } else {
            self.selection.dataset = dataset;

            // Check if dataset is correctly selected
            if (self.isSelected(dataset)) {
                // TODO Don't update dataset info by this way, make watcher instead
                setupDatasetLazyInfo(dataset);

                if (!angular.isObject(data) || data.datasetId !== self.selection.dataset.id || self.isSelected(data)) {
                    self.selection.data = null;
                } else {
                    self.selection.data = data;

                    // Check if data is correctly selected
                    if (self.isSelected(data)) {
                        // TODO Don't update data info by this way, make watcher instead
                        setupDataLazyInfo(data);
                    }
                }

            }
        }

        // Update data preview.
        self.selection.style = null;
        self.updatePreview();
    };

    // Select the style for data preview and.
    self.selectStyle = function(style) {
        self.selection.style = style;
        self.updatePreview();
    };

    // Determines if a dataset/data is selected.
    self.isSelected = function(object) {
        if (angular.isObject(object) && angular.isNumber(object.id)) {
            if (angular.isNumber(object.datasetId)) {
                return self.selection.data && self.selection.data.id === object.id; // object is a data
            } else {
                return self.selection.dataset && self.selection.dataset.id === object.id; // object is a dataset
            }
        }
        return false;
    };

    // Determines if a dataset/data should be visible.
    self.shouldDisplayDataset = function(dataset) {
        return dataset.dataCount >= 0;
    };

    // Returns the data to display for a dataset.
    self.getDataToDisplay = function(dataset) {
        // If the specified dataset is selected, return its data.
        if (self.isSelected(dataset)) {
            setupDatasetLazyInfo(dataset); // ensure that data are well loaded
            return dataset.data;
        }
        // If the dataset contains a single data and if the 'dataset.listing.show_singleton'
        // configuration variable is set to false, return the single data in order to display
        // it instead of the dataset.
        return dataset.data;
    };

    // Display the data in the preview map.
    self.updatePreview = function() {
        if (self.selection.data) {
            // Generate layer name.
            var layerName = self.selection.data.name;
            if (self.selection.data.namespace) {
                layerName = '{' + self.selection.data.namespace + '}' + layerName;
            }

            // Use the pyramid provider identifier for better performances (if expected).
            var providerId = self.selection.data.provider;
            if (CstlConfig['data.overview.use_pyramid'] === true && self.selection.data.pyramidConformProviderId) {
                providerId = self.selection.data.pyramidConformProviderId;
            }

            // Wait for lazy loading completion.
            var targetData = self.selection.data;
            targetData.$infoPromise.then(function() {
                if (targetData !== self.selection.data) {
                    return; // the selection has changed before the promise is resolved
                }

                // Create layer instance.
                var layer;
                if (targetData.targetStyle.length) {
                    self.selection.style = self.selection.style || targetData.targetStyle[0]; // get or set the style selection
                    layer = DataDashboardViewer.createLayerWithStyle(
                        $cookieStore.get('cstlUrl'),
                        targetData.id,
                        layerName,
                        self.selection.style.name,
                        null,
                        null,
                        targetData.type !== 'VECTOR');
                } else {
                    layer = DataDashboardViewer.createLayer(
                        $cookieStore.get('cstlUrl'),
                        targetData.id,
                        layerName,
                        null,
                        targetData.type !== 'VECTOR');
                }
                layer.get('params').ts = new Date().getTime();

                // Display the layer and zoom on its extent.
                self.preview.extent = targetData.extent;
                self.preview.layer = layer;
            });
        } else {
            self.preview.extent = self.preview.layer = undefined;
        }
    };

    // Displays the metadata of the selected data.
    self.openMetadata = function() {
        if (!self.selection.data) {
            return;
        }
        $modal.open({
            templateUrl: 'views/data/modalViewMetadata.html',
            controller: 'ViewMetadataModalController',
            resolve: {
                dashboardName: function() { return 'data'; },
                metadataValues: function() {
                    return Examind.datas.getDataMetadata(self.selection.data.id, true);
                }
            }
        });
    };

    // Open the modal allowing to choose new style associations.
    self.associateStyle = function() {
        if (!self.selection.data) {
            return;
        }
        $modal.open({
            templateUrl: 'views/style/modalStyleChoose.html',
            controller: 'StyleModalController',
            resolve: {
                exclude: function() {
                    return self.selection.data.targetStyle.map(function(style) {
                        return {
                            id: style.id,
                            name: style.name,
                            provider: style.providerIdentifier,
                            type: style.type
                        };
                    });
                },
                selectedLayer: function() {
                    return {
                        id: self.selection.data.id,
                        name: self.selection.data.name,
                        namespace: self.selection.data.namespace,
                        provider: self.selection.data.provider,
                        type: self.selection.data.type
                    };
                },
                selectedStyle: function() { return null; },
                serviceName: function() { return null; },
                newStyle: function() { return null; },
                stylechooser: function() { return null; }
            }
        }).result.then(function(item) {
            if (angular.isObject(item)) {
                var targetData = self.selection.data;
                Examind.styles.link(item.id, targetData.id).then(
                    function() {
                        var style = { id: item.id, name: item.name, providerIdentifier: item.provider };
                        targetData.targetStyle.push(style);
                        if (targetData === self.selection.data) {
                            // If the selection hasn't changed, use the new style in preview.
                            self.selection.style = style;
                            self.updatePreview();
                        }
                    }
                );
            }
        });
    };

    // Break the association between the selected data and a style.
    self.dissociateStyle = function(style) {
        if (!self.selection.data) {
            return;
        }
        var targetData = self.selection.data;
        Examind.datas.deleteStyleAssociation(targetData.id, style.id)
            .then(function() {
                targetData.targetStyle.splice(targetData.targetStyle.indexOf(style), 1);
                if (targetData === self.selection.data && style === self.selection.style) {
                    // If the selection hasn't changed and the removed style was in use, no longer
                    // use this style in preview.
                    self.selection.style = null;
                    self.updatePreview();
                }
            });
    };

    // Open the model allowing the edit the specified style.
    self.editStyle = function(reference) {
        if (!self.selection.data) {
            return;
        }
        Examind.styles.getStyle(reference.id).then(
            function(response) {
                $modal.open({
                    templateUrl: 'views/style/modalStyleEdit.html',
                    controller: 'StyleModalController',
                    resolve: {
                        newStyle: function() { return response.data; },
                        selectedLayer: function() {
                            // TODO - harmonize data POJOs structure
                            return {
                                id: self.selection.data.id,
                                name: self.selection.data.name,
                                namespace: self.selection.data.namespace,
                                provider: self.selection.data.provider,
                                type: self.selection.data.type
                            };
                        },
                        selectedStyle: function() { return null; },
                        serviceName: function() { return null; },
                        exclude: function() {  return null; },
                        stylechooser: function() { return 'edit'; }
                    }
                }).result.then(self.updatePreview);
            }
        );
    };

    // Open the modal allowing to choose new sensor associations.
    self.associateSensor = function() {
        if (!self.selection.data) {
            return;
        }
        $modal.open({
            templateUrl: 'views/sensor/modalSensorChoose.html',
            controller: 'SensorModalChooseController',
            resolve: {
                'selectedData': function() { return self.selection.data; }
            }
        });
    };

    // Break the association between the selected data and a sensor.
    self.dissociateSensor = function(sensor) {
        if (!self.selection.data) {
            return;
        }
        var targetData = self.selection.data;
        Examind.datas.unlinkDataToSensor(targetData.id, sensor.id).then(
            function() {
                targetData.targetSensor.splice(targetData.targetSensor.indexOf(sensor), 1);
            });
    };

    self.openAddDataWizard = function () {
        $location.path('/add_data_workflow');
    };

    // Starts the data import workflow.
    function startDataImport(type, step, editMetadata) {
        $modal.open({
            templateUrl: 'views/data/modalImportData.html',
            controller: 'ModalImportDataController',
            resolve: {
                firstStep: function() { return step; },
                importType: function() { return type; }
            }
        }).result.then(function(result) {
            $scope.$broadcast('reloadDatasets');

            if (!editMetadata || !result || !result.file) {
                return;
            }

            Examind.datas.initMetadata(result.file, result.type, result.completeMetadata)
                .then(
                    function onMetadataInitializationSuccess() {
                        startMetadataEdition(null, result.file, result.type, 'import', 'data');
                    },  function onMetadataInitializationError() {
                        Growl('error', 'Error', 'Unable to prepare metadata for next step.');
                    });
        });
    }

    // Starts the metadata edition for imported data.
    function startMetadataEdition(provider, identifier, type, template, theme) {
        $modal.open({
            templateUrl: 'views/data/modalEditMetadata.html',
            controller: 'EditMetadataModalController',
            resolve: {
                'provider': function() { return provider; },
                'identifier': function() { return identifier; },
                'type': function() { return type; },
                'template': function() { return template; },
                'theme': function() { return theme; }
            }
        });
    }

    // Loads asynchronously dataset advanced information.
    function setupDatasetLazyInfo(dataset, forceReload) {
        if (forceReload === true || !dataset.$infoPromise) {
            // Load the data list (if should be reloaded or if not returned by the server).
            var data = dataset.data;
            if (forceReload || !data || data.length === 0) {
                dataset.$infoPromise = Examind.datas.getDatasetData(dataset.id);
                // Handle promise results.
                dataset.$infoPromise.then(function(response) {
                    dataset.data = response.data;
                    dataset.dataCount = response.data.length;
                });
            }
        }
    }

    // Loads asynchronously data advanced information.
    function setupDataLazyInfo(data, forceReload) {
        if (forceReload === true || !data.$infoPromise) {
            // Load the geographical extent.
            var description = Examind.datas.getGeographicExtent(data.id);

            // Load the associations (styles, services, sensors).
            var associations = Examind.datas.getAssociations(data.id);

            // Combines multiple promises into a single promise.
            data.$infoPromise = $q.all([description, associations]);

            // Handle promise results.
            data.$infoPromise.then(function(results) {
                data.extent = results[0].data.boundingBox;
                data.targetStyle = results[1].data.styles;
                data.targetService = results[1].data.services;
                data.targetSensor = results[1].data.sensors;
            });
        }
    }
    
    function updateMap(evt) {
        self.updatePreview();
    }

    AppConfigService.getConfig(function(config) {

        if (config['cstl.import.empty']) {
            self.addDataWays.push({
                name: 'emptyDataset',
                idHTML: 'emptychoice',
                translateKey: 'label.file.empty',
                defaultTranslateValue: 'Empty dataset',
                bindFunction: function() { startDataImport('empty', 'step2Metadata', true); }
            });
        }

        if (config['cstl.import.custom']) {
            self.addDataWays.push({
                name: 'customDataset',
                idHTML: 'customchoice',
                translateKey: 'label.file.custom',
                defaultTranslateValue: 'Other',
                bindFunction: function() { startDataImport('custom', 'step1Custom', false); }
            });
        }

        // Config param to show the old import data button
        if (config['examind.data.import.old']) {
            self.showOldImportDataBtn = true;
        }
    });
    
    $scope.$on("examind:data:delete", updateMap);
    $scope.$on("examind:dataset:delete", updateMap);
    $scope.$on("examind:dashboard:data:refresh:map", updateMap);
}