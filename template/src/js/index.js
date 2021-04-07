/* Plugins */
import $ from 'jquery'
import middleware from './middleware'

try {
    $(document).ready(() => {
        middleware()
    })
} catch (e) {
}

