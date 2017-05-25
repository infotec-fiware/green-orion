(function() {
    'use strict';

    angular
        .module('greenorionApp')
        .controller('SubscriptionDetailController', SubscriptionDetailController);

    SubscriptionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Subscription'];

    function SubscriptionDetailController($scope, $rootScope, $stateParams, previousState, entity, Subscription) {
        var vm = this;

        vm.subscription = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('greenorionApp:subscriptionUpdate', function(event, result) {
            vm.subscription = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
