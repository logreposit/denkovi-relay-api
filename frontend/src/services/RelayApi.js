import axios from 'axios'

export default {
    fetchRelayStates () {
        return axios.get('')
    },
    turnOnRelay (number) {
        return axios.post('/' + number + '/on')
    },
    turnOffRelay (number) {
        return axios.post('/' + number + '/off')
    }
}
