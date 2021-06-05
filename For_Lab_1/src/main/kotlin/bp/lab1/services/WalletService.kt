package bp.lab1.services

import bp.lab1.reposetory.WalletRepository
import org.springframework.stereotype.Service

@Service
class WalletService(private val walletRepository: WalletRepository) :
    WalletRepository by walletRepository
