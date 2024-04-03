import { defineStore } from "pinia"

export const useActStore = defineStore('act', {
  state: () => { return { index: 1 } }
})