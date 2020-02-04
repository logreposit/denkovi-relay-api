import axios from 'axios'

export default {
    fetchRelayStates () {
        return axios.get('/relays')
    },
    turnOnRelay (number) {
        return axios.post('/relays/' + number + '/on')
    },
    turnOffRelay (number) {
        return axios.post('/relays/' + number + '/off')
    },
    fetchProcedures () {
        return axios.get('/procedures')
    },
    runProcedure (id) {
        return axios.post('/procedures/' + id + '/actions/run')
    }
}
