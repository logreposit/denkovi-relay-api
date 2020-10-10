<template>
    <v-container v-if="loading">
    </v-container>
    <v-container v-else grid-list-xl class="noPaddingTop">
        <v-layout child-flex class="fixedRow">
            <v-row no-gutters>
                <v-col cols="3" v-for="(relay, index) in relays" :key="index">
                    <v-card class="pa-0 text-center relayDisplay" v-bind:class="{ 'green': relay.state === 'ON', 'redasdf': relay.state === 'OFF' }" tile>
                        K {{ relay.number }}
                    </v-card>
                </v-col>
            </v-row>
        </v-layout>

        <v-layout child-flex>
            <v-simple-table>
                <template v-slot:default>
                    <thead>
                        <tr>
                            <th class="relayIdx">#</th>
                            <th>Name</th>
                            <th class="buttonCol"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(procedure, index) in procedures" :key="'p' + index" :class="{ activeRow: false }">
                            <th>P {{index + 1}}</th>
                            <td>{{ procedure.name }}</td>
                            <td>
                                <v-card-actions class="justify-center">
                                    <v-btn text v-show="procedure.ready === true" @click="runProcedure(procedure.id)">Run</v-btn>
                                    <v-btn loading v-show="procedure.ready === false"></v-btn>
                                </v-card-actions>
                            </td>
                        </tr>
                    </tbody>
                </template>
            </v-simple-table>
        </v-layout>

        <v-layout child-flex>
            <v-simple-table>
                <template v-slot:default>
                    <thead>
                        <tr>
                            <th class="relayIdx">#</th>
                            <th>Name</th>
                            <th class="buttonCol"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(relay, index) in relays" :key="'r' + index" :class="{ activeRow: relay.state === 'ON' }">
                            <th>K {{ relay.number }}</th>
                            <td>{{ relay.name }}</td>
                            <td>
                                <v-card-actions class="justify-center">
                                    <v-btn text color="green" v-show="relay.state === 'OFF' && relay.ready === true" @click="turnOnRelay(relay.number)">Turn ON</v-btn>
                                    <v-btn text color="red" v-show="relay.state === 'ON' && relay.ready === true" @click="turnOffRelay(relay.number)">Turn OFF</v-btn>
                                    <v-btn loading v-show="relay.ready === false"></v-btn>
                                </v-card-actions>
                            </td>
                        </tr>
                    </tbody>
                </template>
            </v-simple-table>
        </v-layout>
    </v-container>
</template>

<style scoped>
    .activeRow {
        background-color: rgba(232, 245, 233, 1);
    }
    .activeRow:hover {
        background-color: rgba(232, 245, 233, 1) !important;
    }
    .buttonCol {
        min-width: 168px;
    }
    .relayIdx {
        min-width: 58px;
    }
    .relayDisplay {
        box-shadow: none;
    }
    .fixedRow {
        position: sticky;
        top: 52px;
        z-index: 1;
        /*padding-bottom: 12px;*/
    }
    .noPaddingTop {
        padding-top: 0;
    }
</style>

<script>
import relayApi from '@/services/RelayApi'
import Vue from 'vue'
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

// IMPORTANT!!
// https://github.com/anoobbava/movie-app

export default {
    data () {
        return {
            'loading': true,
            'relays': [],
            'procedures': []
        }
    },
    mounted () {
        this.initialLoad();
        //this.refreshTimer = setInterval(this.initialLoad, 7500)
        this.initializeSockets();
    },
    beforeDestroy () {
        //clearInterval(this.refreshTimer)
    },
    methods: {
        turnOnRelay (number) {
            this.setRelayReady(number, false);
            relayApi.turnOnRelay(number)
                .then(response => {
                    this.setRelay(response.data.number, response.data.state);
                    this.setRelayReady(number, true);
                })
                .catch(error => console.error(error))
        },
        turnOffRelay (number) {
            this.setRelayReady(number, false);
            relayApi.turnOffRelay(number)
                .then(response => {
                    this.setRelay(response.data.number, response.data.state);
                    this.setRelayReady(number, true);
                })
                .catch(error => console.error(error))
        },
        initialLoad () {
            relayApi.fetchRelayStates()
                .then(response => {
                    this.relays = response.data;
                    this.relays.forEach((relay) => {
                        relay.ready = true;
                    });
                    this.loading = false;
                })
                .catch(error => console.error(error))
            relayApi.fetchProcedures()
                .then(response => {
                    this.procedures = response.data;
                    this.procedures.forEach((procedure) => {
                        procedure.ready = true
                    })
                })
        },
        initializeSockets () {
            let socket = new SockJS('/socket');
            let stompClient = Stomp.over(socket);

            stompClient.connect({}, (frame) => {
                console.log('Connected: ' + frame);
                stompClient.subscribe("/relays", (msg) => {
                    this.processMessage(msg);
                });
                stompClient.subscribe("/procedures/completed", (msg) => {
                    this.processProcedureCompletedMessage(msg);
                })
            });
        },
        processMessage (val) {
            let relay = JSON.parse(val.body);
            console.log('Relay Changed Event: ', relay);
            this.setRelay(relay.number, relay.state);
        },
        processProcedureCompletedMessage (val) {
            let procedureCompleted = JSON.parse(val.body);
            console.log('Procedure Completed Event: ', procedureCompleted);
            this.setProcedureReady(procedureCompleted.id, true);
        },
        setRelay (number, state) {
            this.relays.forEach((array) => {
                if (array.number === number) {
                    array.state = state
                }
            })
        },
        setRelayReady (number, ready) {
            this.relays.forEach((relay, index) => {
                if (relay.number === number && relay.ready !== ready) {
                    relay.ready = ready;
                    Vue.set(this.relays, index, relay)
                }
            })
        },
        runProcedure (id) {
            this.setProcedureReady(id, false);
            relayApi.runProcedure(id)
                .then(response => {
                    console.log(response)
                })
                .catch(error => console.error(error))
        },
        setProcedureReady (id, ready) {
            this.procedures.forEach((procedure, index) => {
                if (procedure.id === id && procedure.ready !== ready) {
                    procedure.ready = ready;
                    Vue.set(this.procedures, index, procedure)
                }
            })
        }
    }
}
</script>
