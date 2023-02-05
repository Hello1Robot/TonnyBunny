export default {
    /*
        NBunny
     */
    TOGGLE_BUNNY_MODAL(state) {
        state.bunny.isBunnyModalOpen = !state.bunny.isBunnyModalOpen;
    },
    SET_BUNNY_SEQ(state, data) {
        state.bunny.bunnySeq = data;
    },

    SET_BUNNY_DETAIL(state, data) {
        state.board.bunnyDetail = data;
    },
    SET_BUNNY_List(state, data) {
        state.board.bunnyList = data;
    },
};
