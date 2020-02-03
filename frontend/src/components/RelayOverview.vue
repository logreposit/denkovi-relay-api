<template>
    <v-container v-if="loading">
    </v-container>
    <v-container v-else grid-list-xl>
        <v-layout wrap>
            <v-flex md3 sm6 xs12 v-for="(relay, index) in relays" :key="index" mb-2>
                <v-card>
                    <v-card-title primary-title>
                        Relay {{ relay.number }}
                    </v-card-title>
                    <v-card-text>
                        <p v-if="relay.name">{{ relay.name }}</p>
                        Relay is
                        <span v-show="relay.state == 'ON'" class="green--text">{{ relay.state }}</span>
                        <span v-show="relay.state == 'OFF'" class="red--text">{{ relay.state }}</span>
                    </v-card-text>
                    <v-card-actions class="justify-center">
                        <v-btn text color="green" v-show="relay.state === 'OFF' && relay.ready === true" @click="turnOnRelay(relay.number)">Turn ON</v-btn>
                        <v-btn text color="red" v-show="relay.state === 'ON' && relay.ready === true" @click="turnOffRelay(relay.number)">Turn OFF</v-btn>
                        <v-btn loading v-show="relay.ready === false"></v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

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
            'relays': []
        }
    },
    mounted () {
        this.initialLoad();
        //this.refreshTimer = setInterval(this.initialLoad, 7500)

        this.$nextTick(function () {
            let socket = new SockJS('/socket');
            let stompClient = Stomp.over(socket);

            stompClient.connect(
                {},
                function(frame) {
                    console.log('Connected: ' + frame);

                    stompClient.subscribe("/relays", function(val) {
                        console.log(val);
                        console.log(JSON.parse(val.body));
                        //vm.valuesList = JSON.parse(val.body); // TODO DoM
                    });
                }
            );
        })
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
    }
}
</script>
